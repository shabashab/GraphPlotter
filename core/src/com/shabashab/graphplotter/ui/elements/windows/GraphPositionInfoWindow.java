package com.shabashab.graphplotter.ui.elements.windows;

import com.shabashab.graphplotter.ui.GuiElementsPool;
import com.shabashab.graphplotter.ui.elements.base.ImGuiWindow;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import imgui.flag.ImGuiCond;

public class GraphPositionInfoWindow extends ImGuiWindow {
  private final GraphPosition _position;

  public GraphPositionInfoWindow(GuiElementsPool pool) {
    super(pool, "Graph position info");

    setSize(300, 0, ImGuiCond.FirstUseEver);

    _position = pool.getGraphWindow().getGraphActor().getPosition();
  }

  @Override
  protected void setup() {
    ImGui.text("Graph position X: " + _position.getXOffset() * -1);
    ImGui.text("Graph position Y: " + _position.getYOffset() * -1);
    ImGui.text("Graph scale X: " + _position.getXScale());
    ImGui.text("Graph scale Y: " + _position.getYScale());
  }
}
