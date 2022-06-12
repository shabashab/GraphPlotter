package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiMainMenuBar;
import imgui.ImGui;

public class MainMenuBar extends ImGuiMainMenuBar {
  public MainMenuBar(GuiElementsPool elementsPool) {
    super(elementsPool);
  }

  @Override
  protected void setup() {
    if (ImGui.beginMenu("File")) {
      if (ImGui.menuItem("Exit")) {
        System.exit(0);
      }
      ImGui.endMenu();
    }

    if (ImGui.beginMenu("Plot")) {
      if (ImGui.menuItem("Save to file...")) {
        getElementsPool().getSavePlotPopup().open();
      }
      ImGui.endMenu();
    }

//    if(ImGui.beginMenu("View")) {
//      if(ImGui.menuItem("Toggle metrics")) {
//        _showMetricsWindow = !_showMetricsWindow;
//      }
//      ImGui.endMenu();
//    }
  }
}
