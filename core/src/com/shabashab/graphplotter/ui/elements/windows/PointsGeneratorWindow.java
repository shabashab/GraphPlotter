package com.shabashab.graphplotter.ui.elements.windows;

import com.badlogic.gdx.math.Vector2;
import com.shabashab.graphplotter.ui.GuiElementsPool;
import com.shabashab.graphplotter.ui.elements.base.ImGuiWindow;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class PointsGeneratorWindow extends ImGuiWindow {
  boolean _firstUse = true;

  private final ImInt _pointsCount;
  private final ImFloat _rangeMin;
  private final ImFloat _rangeMax;


  public PointsGeneratorWindow(GuiElementsPool pool) {
    super(pool, "Points Generator Options");

    _pointsCount = new ImInt(1235);
    _rangeMin = new ImFloat(-200f);
    _rangeMax = new ImFloat(200f);

    setSize(200, 0, ImGuiCond.FirstUseEver);
  }

  @Override
  protected void setup() {
    if(_firstUse) {
      _firstUse = false;

      generateAndUpdatePoints();
    }

    float width = ImGui.getContentRegionAvailX();

    ImGui.labelText("##pointsCount", "Points count:");
    ImGui.setNextItemWidth(width);
    ImGui.inputInt("##pointsCount", _pointsCount);
    ImGui.labelText("##minval", "Min value (in pi):");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##minval", _rangeMin);
    ImGui.labelText("##maxval", "Max value (in pi):");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##maxval", _rangeMax);

    if(ImGui.button("Generate points")) {
      onGeneratePointsButtonClick();
    }
  }

  private void generateAndUpdatePoints() {
    Vector2[] points =generatePointsWidthParameters();
    this.getElementsPool().getGraphWindow().getGraphActor().updatePoints(points);
  }

  private void onGeneratePointsButtonClick() {
    String errorMessage = validateInputValues();

    if(errorMessage.length() != 0) {
      getElementsPool().getErrorPopup().setErrorMessage(errorMessage);
      getElementsPool().getErrorPopup().open();
      return;
    }

    Vector2[] points = generatePointsWidthParameters();
    this.getElementsPool().getGraphWindow().getGraphActor().updatePoints(points);
  }

  private Vector2[] generatePointsWidthParameters() {
    return generatePoints(_pointsCount.get(), _rangeMin.get(), _rangeMax.get());
  }

  private String validateInputValues() {
    if(_pointsCount.get() <= 0) {
      return String.format("Points count can't be less or equal to 0. Your input is %d", _pointsCount.get());

    }

    if(_rangeMin.get() >= _rangeMax.get()) {
      return "The minimum of the range can't be greater or equal to the maximum of the range.";
    }

    return "";
  }

  private static Vector2[] generatePoints(int pointsCount, float min, float max) {
    Vector2[] points = new Vector2[pointsCount];

    float step = (max - min) / pointsCount;
    for(int i = 0; i < pointsCount; i++) {
      float a = (float)((min + step * i) * Math.PI);
      float r = (float)Math.sin(a * 0.99);

      float x = r * (float)Math.cos(a);
      float y = r * (float)Math.sin(a);

      points[i] = new Vector2(x, y);
    }

    return points;
  }
}
