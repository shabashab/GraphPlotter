package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.shabashab.graphplotter.input.LockerEventListener;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;

public class UIActor extends Actor {
  private boolean _showMetricsWindow;

  private final LockerEventListener _lockerEventListener;
  private final GraphWindow _graphWindow;
  private final ImGuiPopupManager _popupManager;
  private final SavePlotPopup _savePlotPopup;

  public UIActor() {
    ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

    _graphWindow = new GraphWindow();
    _graphWindow.setSize(600, 600);

    _lockerEventListener = new LockerEventListener(false);

    addListener(_lockerEventListener);
    addListener(_graphWindow.getEventListener());

    _popupManager = new ImGuiPopupManager();
    _savePlotPopup = new SavePlotPopup();
    _popupManager.addPopup(_savePlotPopup);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    ImGuiHelper.getImGuiGlfw().newFrame();
    ImGui.newFrame();

    ImGui.dockSpaceOverViewport(ImGui.getMainViewport());

    ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);

    if (ImGui.beginMainMenuBar()) {
      if (ImGui.beginMenu("File")) {
        if (ImGui.menuItem("Exit")) {
          System.exit(0);
        }
        ImGui.endMenu();
      }

      if (ImGui.beginMenu("Plot")) {
        if (ImGui.menuItem("Save to file...")) {
          _savePlotPopup.open();
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

    ImGui.setNextWindowSize(600, 600, ImGuiCond.FirstUseEver);
    _graphWindow.draw();

    boolean lock = !_graphWindow.getIsInFocus() && (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse());
    _lockerEventListener.setIsLocked(lock);

    if(_showMetricsWindow)
      ImGui.showMetricsWindow();

    _popupManager.renderPopups();

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }
}
