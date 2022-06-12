package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiPopup;
import imgui.ImGui;

public class SavePlotPopup extends ImGuiPopup {
  public SavePlotPopup(GuiElementsPool elementsPool) {
    super(elementsPool, "Save plot image");
  }

  @Override
  protected void setup() {
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
