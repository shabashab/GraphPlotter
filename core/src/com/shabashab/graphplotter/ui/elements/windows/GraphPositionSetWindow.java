package com.shabashab.graphplotter.ui.elements.windows;

import com.shabashab.graphplotter.ui.GuiElementsPool;
import com.shabashab.graphplotter.ui.elements.base.ImGuiWindow;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import imgui.type.ImFloat;

public class GraphPositionSetWindow extends ImGuiWindow {
  private final GraphPosition _position;

  private final ImFloat _scaleX;
  private final ImFloat _scaleY;
  private final ImFloat _positionX;
  private final ImFloat _positionY;

  public GraphPositionSetWindow(GuiElementsPool pool) {
    super(pool, "Set graph position");

    _position = pool.getGraphWindow().getGraphActor().getPosition();
    _scaleX = new ImFloat(_position.getXScale());
    _scaleY = new ImFloat(_position.getYScale());
    _positionX = new ImFloat(_position.getXOffset() * -1);
    _positionY = new ImFloat(_position.getYOffset() * -1);
  }

  @Override
  protected void setup() {
    float width = ImGui.getContentRegionAvailX();

    ImGui.labelText("##posX", "Position X");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##posX", _positionX);

    ImGui.labelText("##posY", "Position Y");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##posY", _positionY);

    ImGui.labelText("##scaleX", "Scale X");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##scaleX", _scaleX);

    ImGui.labelText("##scaleY", "Scale Y");
    ImGui.setNextItemWidth(width);
    ImGui.inputFloat("##scaleY", _scaleY);

    if(ImGui.button("Apply")) {
      _position.setOffset(_positionX.get() * -1, _positionY.get() * -1);
      _position.setScale(_scaleX.get(), _scaleY.get());
    }
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(300, 0);
  }
}
