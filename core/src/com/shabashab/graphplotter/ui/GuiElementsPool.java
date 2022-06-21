package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.MainMenuBar;
import com.shabashab.graphplotter.ui.elements.popups.ErrorPopup;
import com.shabashab.graphplotter.ui.elements.popups.SavePlotPopup;
import com.shabashab.graphplotter.ui.elements.windows.GraphWindow;
import com.shabashab.graphplotter.ui.elements.windows.PointsGeneratorWindow;

public interface GuiElementsPool {
  GraphWindow getGraphWindow();
  PointsGeneratorWindow getPointsGeneratorWindow();

  MainMenuBar getMainMenuBar();
  SavePlotPopup getSavePlotPopup();
  ErrorPopup getErrorPopup();

}
