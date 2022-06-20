package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiWindow;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import imgui.type.ImFloat;

public class GraphPositionSetWindow extends ImGuiWindow {
  private GraphPosition _position;

  private ImFloat _scaleX;
  private ImFloat _scaleY;
  private ImFloat _positionX;
  private ImFloat _positionY;

  protected GraphPositionSetWindow(GuiElementsPool pool) {
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
