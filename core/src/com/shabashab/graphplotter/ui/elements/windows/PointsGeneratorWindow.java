package com.shabashab.graphplotter.ui.elements.windows;

import com.badlogic.gdx.math.Vector2;
import com.shabashab.graphplotter.ui.GuiElementsPool;
import com.shabashab.graphplotter.ui.elements.base.ImGuiWindow;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class PointsGeneratorWindow extends ImGuiWindow {
  boolean firstUse = true;
  ImInt pointsCountValue;

  ImFloat rangeMin;
  ImFloat rangeMax;

  public PointsGeneratorWindow(GuiElementsPool pool) {
    super(pool, "Points Generator Options");

    pointsCountValue = new ImInt(1235);

    rangeMin = new ImFloat(-200f);
    rangeMax = new ImFloat(200f);
  }

  @Override
  protected void setup() {
    if(firstUse) {
      firstUse = false;

      Vector2[] points = generatePoints();
      this.getElementsPool().getGraphWindow().getGraphActor().updatePoints(points);
    }

    float width = ImGui.getContentRegionAvailX();

    ImGui.labelText("##pointsCount", "Points count:");
    ImGui.setNextItemWidth(width);
    ImGui.inputInt("##pointsCount", pointsCountValue);
    ImGui.labelText("##minval", "Min value (in pi):");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##minval", rangeMin);
    ImGui.labelText("##maxval", "Max value (in pi):");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##maxval", rangeMax);

    if(ImGui.button("Generate points")) {
      if(pointsCountValue.get() <= 0) {
        String errorMessage = String.format("Points count can't be less or equal to 0. Your input is %d", pointsCountValue.get());

        getElementsPool().getErrorPopup().setErrorMessage(errorMessage);
        getElementsPool().getErrorPopup().open();
      } else if(rangeMin.get() >= rangeMax.get()) {
        String errorMessage = "The minimum of the range can't be greater or equal to the maximum of the range.";

        getElementsPool().getErrorPopup().setErrorMessage(errorMessage);
        getElementsPool().getErrorPopup().open();
      } else {
        Vector2[] points = generatePoints();
        this.getElementsPool().getGraphWindow().getGraphActor().updatePoints(points);
      }
    }
  }

  private Vector2[] generatePoints() {
    return generatePoints(pointsCountValue.get(), rangeMin.get(), rangeMax.get());
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

  private static Vector2[] generatePointsByStep(float step, float min, int stepsCount) {
    Vector2[] points = new Vector2[stepsCount];

    for(int i = 0; i < stepsCount; i++) {
      float a = (float)((min + step * i) * Math.PI);
      float r = (float)Math.sin(a * 0.99);

      float x = r * (float)Math.cos(a);
      float y = r * (float)Math.sin(a);

      points[i] = new Vector2(x, y);
    }

    return points;
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(200, 0, ImGuiCond.FirstUseEver);
  }
}
