package com.shabashab.graphplotter.input;


import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class LockerEventListener implements EventListener {
  private boolean _isLocked;

  public LockerEventListener(boolean defaultState) {
    _isLocked = defaultState;
  }

  public void setIsLocked(boolean isLocked) {
    _isLocked = isLocked;
  }

  @Override
  public boolean handle(Event event) {
    if(_isLocked)
      event.cancel();

    return _isLocked;
  }
}
