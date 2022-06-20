package com.shabashab.graphplotter.ui;

public interface GuiElementsPool {
  GraphWindow getGraphWindow();
  PointsGeneratorWindow getPointsGeneratorWindow();

  MainMenuBar getMainMenuBar();
  SavePlotPopup getSavePlotPopup();
  ErrorPopup getErrorPopup();

}
