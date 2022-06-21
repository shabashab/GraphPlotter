package com.shabashab.graphplotter.ui.elements.base;

import com.shabashab.graphplotter.ui.GUIScene;
import com.shabashab.graphplotter.ui.GuiElementsPool;

public abstract class ImGuiRenderable {
  private final GuiElementsPool _elementsPool;

  public ImGuiRenderable(GuiElementsPool elementsPool) {
    _elementsPool = elementsPool;
  }

  protected GuiElementsPool getElementsPool() {
    return _elementsPool;
  }

  protected void beforeBegin() {}

  protected boolean shouldBegin() {return true;}
  protected abstract boolean begin();
  protected abstract void end();

  protected void afterEnd(boolean beenRendered) {}

  protected abstract void setup();

  public void render() {
    if(!shouldBegin()) {
      return;
    }

    beforeBegin();
    boolean render = begin();

    if(render) {
      setup();
    }

    end();
    afterEnd(render);
  }
}
