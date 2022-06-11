package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import imgui.ImGui;
import imgui.flag.ImGuiComboFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;

public class UIActor extends Actor {
  private boolean _showMetricsWindow;
  GraphWindow _graphWindow;

  public UIActor() {
    ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

    _graphWindow = new GraphWindow();
    _graphWindow.setSize(600, 600);

    addListener(_graphWindow.getEventListener());
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    ImGuiHelper.getImGuiGlfw().newFrame();

    ImGui.newFrame();

    ImGui.dockSpaceOverViewport(ImGui.getMainViewport());

    ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);

    boolean showPopup = false;

    if (ImGui.beginMainMenuBar()) {
      if (ImGui.beginMenu("File")) {
        if (ImGui.menuItem("Exit")) {
          System.exit(0);
        }
        ImGui.endMenu();
      }

      if (ImGui.beginMenu("Plot")) {
        if (ImGui.menuItem("Save to file...")) {
          showPopup = true;
        }
        ImGui.endMenu();
      }

      if(ImGui.beginMenu("View")) {
        if(ImGui.menuItem("Toggle metrics")) {
          _showMetricsWindow = !_showMetricsWindow;
        }
        ImGui.endMenu();
      }

      ImGui.endMainMenuBar();
    }

    if (showPopup) {
      ImGui.openPopup("popup");
    }

    ImGui.setNextWindowSize(300, 300);
    if (ImGui.beginPopupModal("popup")) {
      String[] comboItems = new String[]{
              "JPG Image",
              "PNG Image"
      };

      if(ImGui.beginCombo("File type: ", comboItems[0], ImGuiComboFlags.PopupAlignLeft)) {
        for(String comboItem: comboItems) {
          if(ImGui.selectable(comboItem)) {
            ImGui.setItemDefaultFocus();
          }
        }
        ImGui.endCombo();
      }

      if (ImGui.button("Close")) {
        ImGui.closeCurrentPopup();
      }
      ImGui.endPopup();
    }

    ImGui.setNextWindowSize(600, 600, ImGuiCond.FirstUseEver);
    _graphWindow.draw();

    if(_showMetricsWindow)
      ImGui.showMetricsWindow();

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }
}
