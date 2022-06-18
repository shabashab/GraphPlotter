package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.math.Vector2;
import com.shabashab.graphplotter.ui.elements.ImGuiWindow;
import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public class PointsGeneratorWindow extends ImGuiWindow {
  ImInt pointsCountValue;

  ImFloat rangeMin;
  ImFloat rangeMax;

  protected PointsGeneratorWindow(GuiElementsPool pool) {
    super(pool, "Points Generator Options");

    pointsCountValue = new ImInt(1000);

    rangeMin = new ImFloat(-10f);
    rangeMax = new ImFloat(10f);
  }

  @Override
  protected void setup() {
    ImGui.inputInt("Points count", pointsCountValue);
    ImGui.inputFloat("Range MIN", rangeMin);
    ImGui.inputFloat("Range MAX", rangeMax);
    if(ImGui.button("Generate points")) {
      Vector2[] points = generatePoints();
      this.getElementsPool().getGraphWindow().getGraphActor().updatePoints(points);
    }
  }

  private static float fun(float x) {
    return (float)Math.sin(x);
  }

  private Vector2[] generatePoints() {
    return generatePoints(pointsCountValue.get(), rangeMin.get(), rangeMax.get());
  }

  private static Vector2[] generatePoints(int pointsCount, float min, float max) {
    Vector2[] points = new Vector2[pointsCount];

    float step = (max - min) / pointsCount;
    for(int i = 0; i < pointsCount; i++) {
      float x = min + step * i;
      points[i] = new Vector2(x, fun(x));
    }

    return points;
  }
}
