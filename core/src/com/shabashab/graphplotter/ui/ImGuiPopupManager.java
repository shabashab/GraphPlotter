package com.shabashab.graphplotter.ui;

import java.util.ArrayList;

public class ImGuiPopupManager {
  private final ArrayList<ImGuiPopup> _popups;

  public ImGuiPopupManager() {
    _popups = new ArrayList<>();
  }

  public void addPopup(ImGuiPopup dialog) {
    _popups.add(dialog);
  }

  public void renderPopups() {
    for(ImGuiPopup dialog: _popups) {
      if(dialog.getShouldOpen()) {
        dialog.render();
      }
    }
  }
}
