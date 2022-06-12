package com.shabashab.graphplotter.ui;

import imgui.ImGui;

public abstract class ImGuiPopup {
  private final String _name;
  private boolean _shouldOpen;

  public ImGuiPopup(String name) {
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

  public void render() {
    ImGui.openPopup(_name);

    beforeBegin();
    if(ImGui.beginPopupModal(_name)) {
      setupDialog();
      ImGui.endPopup();
    }
  }

  protected void beforeBegin() {}

  protected abstract void setupDialog();
}
