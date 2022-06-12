package com.shabashab.graphplotter.ui;

import imgui.ImGui;

public class SavePlotPopup extends ImGuiPopup {
  public SavePlotPopup() {
    super("Save plot image");
  }

  @Override
  protected void setupDialog() {
    ImGui.text("Hello, world!");
    if(ImGui.button("Close")) {
      close();
    }
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(300, 300);
  }
}
