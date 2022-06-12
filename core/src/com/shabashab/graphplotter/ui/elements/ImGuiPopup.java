package com.shabashab.graphplotter.ui.elements;

import com.shabashab.graphplotter.ui.GuiElementsPool;
import imgui.ImGui;

public abstract class ImGuiPopup extends ImGuiRenderable {
  private final String _name;
  private boolean _shouldOpen;

  public ImGuiPopup(GuiElementsPool elementsPool, String name) {
    super(elementsPool);

    _name = name;
    _shouldOpen = false;
  }

  public boolean getShouldOpen() {
    return _shouldOpen;
  }

  public void open() {
    _shouldOpen = true;
  }

  public void close() {
    if(!_shouldOpen)
      throw new IllegalStateException("Can't close popup. Not opened");

    ImGui.closeCurrentPopup();
    _shouldOpen = false;
  }

  @Override
  protected boolean begin() {
    if(!_shouldOpen)
      return false;

    ImGui.openPopup(_name);

    return ImGui.beginPopupModal(_name);
  }

  @Override
  protected void end() {
    ImGui.endPopup();
  }
}
