package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiWindow;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import imgui.flag.ImGuiCond;

public class GraphPositionInfoWindow extends ImGuiWindow {
  private GraphPosition _position;

  protected GraphPositionInfoWindow(GuiElementsPool pool) {
    super(pool, "Graph position info");

    _position = pool.getGraphWindow().getGraphActor().getPosition();
  }

  @Override
  protected void setup() {
    ImGui.text("Graph position X: " + _position.getXOffset() * -1);
    ImGui.text("Graph position Y: " + _position.getYOffset() * -1);
    ImGui.text("Graph scale X: " + _position.getXScale());
    ImGui.text("Graph scale Y: " + _position.getYScale());
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(300, 0, ImGuiCond.FirstUseEver);
  }
}
