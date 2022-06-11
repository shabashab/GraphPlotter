package com.shabashab.graphplotter.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class UIEventListener implements EventListener {
  private final Vector2 _graphViewPosition;
  private final Vector2 _graphViewSize;
  private final GraphPositionInputListener _graphPositionInputListener;

  public UIEventListener(GraphPositionInputListener listener, Vector2 graphViewPosition, Vector2 graphViewSize) {
    _graphPositionInputListener = listener;
    _graphViewPosition = graphViewPosition;
    _graphViewSize = graphViewSize;
  }

  public void updateGraphViewBounds(float x, float y, float width, float height) {
    _graphViewPosition.set(x, y);
    _graphViewSize.set(width, height);
  }

  @Override
  public boolean handle(Event event) {
    int mouseX = Gdx.input.getX();
    int mouseY = Gdx.input.getY();

    boolean isInXBounds = mouseX >= _graphViewPosition.x && mouseX <= _graphViewPosition.x + _graphViewSize.x;
    boolean isInYBounds = mouseY >= _graphViewPosition.y && mouseY <= _graphViewPosition.y + _graphViewSize.y;

    if(isInXBounds && isInYBounds) {
      return _graphPositionInputListener.handle(event);
    }

    return false;
  }
}
