package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiPopup;
import imgui.ImGui;

public class ErrorPopup extends ImGuiPopup {
  private String _errorMessage;

  public ErrorPopup(GuiElementsPool elementsPool) {
    super(elementsPool, "Error!");
    _errorMessage = "";
  }

  public void setErrorMessage(String errorMessage) {
    _errorMessage = errorMessage;
  }

  @Override
  protected void setup() {
    ImGui.text(_errorMessage);
    if(ImGui.button("OK")) {
      close();
    }
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(0, 0);
  }
}
