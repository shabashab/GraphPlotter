package com.shabashab.graphplotter.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.utils.GraphPosition;

public class GraphPositionInputListener extends InputListener {
  private final GraphPosition _position;
  private final GraphActor _actor;

  private Vector2 _previousMousePosition;
  private boolean _isCtrlPressed = false;
  private boolean _isShiftPressed = false;

  public GraphPositionInputListener(GraphPosition position, GraphActor actor) {
    _position = position;
    _actor = actor;
  }

  @Override
  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    _previousMousePosition = new Vector2(x, y);
    return true;
  }

  @Override
  public void touchDragged(InputEvent event, float x, float y, int pointer) {
    float scalarX = _position.getXScale() * 2 / _actor.getWidth();
    float scalarY = _position.getYScale() * 2 / _actor.getHeight();

    Vector2 mousePosition = new Vector2(x, y);

    _position.setOffset(_position.getXOffset() + (mousePosition.x - _previousMousePosition.x) * scalarX, _position.getYOffset() + (mousePosition.y - _previousMousePosition.y) * scalarY);
    _previousMousePosition = mousePosition;
  }

  @Override
  public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
    float multiplier = (amountY * 0.75f) + 1.25f;

    if(_isCtrlPressed) {
      _position.setScale(_position.getXScale() * multiplier, _position.getYScale());
      return true;
    }

    if(_isShiftPressed) {
      _position.setScale(_position.getXScale(), _position.getYScale() * multiplier);
      return true;
    }

    _position.setScale(_position.getXScale() * multiplier, _position.getYScale() * multiplier);
    return true;
  }

  @Override
  public boolean keyDown(InputEvent event, int keycode) {
    if(keycode == Input.Keys.CONTROL_LEFT)
      _isCtrlPressed = true;

    if(keycode == Input.Keys.SHIFT_LEFT)
      _isShiftPressed = true;

    return super.keyDown(event, keycode);
  }

  @Override
  public boolean keyUp(InputEvent event, int keycode) {
    if(keycode == Input.Keys.CONTROL_LEFT)
      _isCtrlPressed = false;

    if(keycode == Input.Keys.SHIFT_LEFT)
      _isShiftPressed = false;

    return super.keyUp(event, keycode);
  }
}
