package com.shabashab.graphplotter.ui;

import com.shabashab.graphplotter.ui.elements.ImGuiPopup;
import com.shabashab.graphplotter.ui.elements.ImGuiRenderable;

import java.util.ArrayList;

public class ImGuiPopupManager extends ImGuiRenderable {
  private final ArrayList<ImGuiPopup> _popups;

  public ImGuiPopupManager(GuiElementsPool pool) {
    super(pool);
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

  @Override
  protected boolean begin() {
    return !_popups.isEmpty();
  }

  @Override
  protected void end() {
    //nothing
  }

  @Override
  protected void setup() {
    renderPopups();
  }
}
