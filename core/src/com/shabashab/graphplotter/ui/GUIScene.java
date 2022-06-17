package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.input.GraphPositionInputListener;
import com.shabashab.graphplotter.input.GraphWindowEventListenerRouter;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;

public class GUIScene extends ImGuiScene implements GuiElementsPool {
  private GraphWindow _graphWindow;
  private PointsGeneratorWindow _pointsGeneratorWindow;
  private MainMenuBar _mainMenuBar;
  private SavePlotPopup _savePlotPopup;

  @Override
  public GraphWindow getGraphWindow() {
    return _graphWindow;
  }

  @Override
  public PointsGeneratorWindow getPointsGeneratorWindow() {
    return _pointsGeneratorWindow;
  }

  @Override
  public MainMenuBar getMainMenuBar() {
    return _mainMenuBar;
  }

  @Override
  public SavePlotPopup getSavePlotPopup() {
    return _savePlotPopup;
  }

  @Override
  protected void create() {
    ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

    ImGuiPopupManager _popupManager = new ImGuiPopupManager(this);

    _mainMenuBar = new MainMenuBar(this);
    addToRenderQueue(_mainMenuBar);

    _graphWindow = new GraphWindow(this);
    addToRenderQueue(_graphWindow);

    _pointsGeneratorWindow = new PointsGeneratorWindow(this);
    addToRenderQueue(_pointsGeneratorWindow);

    _savePlotPopup = new SavePlotPopup(this);
    _popupManager.addPopup(_savePlotPopup);

    addToRenderQueue(_popupManager);

    addListener(new GraphWindowEventListenerRouter(_graphWindow, new GraphPositionInputListener(_graphWindow.getGraphActor()), 20));
  }

  @Override
  protected void beforeQueueRender() {
    ImGui.dockSpaceOverViewport(ImGui.getMainViewport());
    ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
  }
}
