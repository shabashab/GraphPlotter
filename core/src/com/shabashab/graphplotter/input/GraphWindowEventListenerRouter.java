package com.shabashab.graphplotter.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.shabashab.graphplotter.ui.elements.windows.GraphWindow;

public final class GraphWindowEventListenerRouter extends ConditionalEventListenerRouter {
  private final GraphWindow _graphWindow;
  private final float _padding;

  public GraphWindowEventListenerRouter(GraphWindow window, EventListener routeTo, float padding) {
    super(routeTo);
    _graphWindow = window;
    _padding = padding;
  }

  @Override
  protected boolean checkEvent(Event event) {
    if(!_graphWindow.getIsInFocus())
      return false;

    int mouseX = Gdx.input.getX();
    int mouseY = Gdx.input.getY();

    float windowX = _graphWindow.getX();
    float windowY = _graphWindow.getY();
    float windowWidth = _graphWindow.getWidth();
    float windowHeight = _graphWindow.getHeight();

    boolean isInXBounds = mouseX >= windowX + _padding && mouseX <= windowX + windowWidth - _padding;
    boolean isInYBounds = mouseY >= windowY + _padding && mouseY <= windowY + windowHeight - _padding;

    return isInXBounds && isInYBounds;
  }
}
