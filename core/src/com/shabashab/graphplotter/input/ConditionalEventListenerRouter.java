package com.shabashab.graphplotter.input;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class ConditionalEventListenerRouter implements EventListener {
  private final EventListener _routeTo;

  public ConditionalEventListenerRouter(EventListener routeTo) {
    _routeTo = routeTo;
  }

  @Override
  public boolean handle(Event event) {
    if(checkEvent(event)) {
      return _routeTo.handle(event);
    }
    return false;
  }

  protected abstract boolean checkEvent(Event event);
}
