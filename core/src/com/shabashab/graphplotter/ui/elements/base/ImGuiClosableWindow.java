package com.shabashab.graphplotter.ui.elements.base;

import com.shabashab.graphplotter.ui.GuiElementsPool;

public abstract class ImGuiClosableWindow extends ImGuiWindow {
  private boolean _isOpened;

  protected ImGuiClosableWindow(GuiElementsPool pool, String title) {
    super(pool, title);

    _isOpened = false;
  }

  public void open() {
    _isOpened = true;
  }

  public void close() {
    _isOpened = false;
  }

  public boolean isOpened() {
    return _isOpened;
  }

  @Override
  protected boolean shouldBegin() {
    return _isOpened;
  }

}
