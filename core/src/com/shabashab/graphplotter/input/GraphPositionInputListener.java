package com.shabashab.graphplotter.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.utils.GraphPosition;

public class GraphPositionInputListener extends InputListener {
  private static final float ZOOM_FACTOR = 0.1f;
  private static final float POSITIVE_ZOOM_FACTOR = ZOOM_FACTOR + 1;
  private static final float NEGATIVE_ZOOM_FACTOR = 1 / POSITIVE_ZOOM_FACTOR;

  private final GraphPosition _position;
  private final GraphActor _actor;

  private Vector2 _previousMousePosition;
  private boolean _isCtrlPressed = false;
  private boolean _isShiftPressed = false;

  public GraphPositionInputListener(GraphActor actor) {
    _position = actor.getPosition();
    _actor = actor;
  }

  @Override
  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    _previousMousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

    return true;
  }

  @Override
  public void touchDragged(InputEvent event, float x, float y, int pointer) {
    float scalarX = _position.getXScale() * 2 / _actor.getWidth();
    float scalarY = _position.getYScale() * 2 / _actor.getHeight();

    Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

    _position.setOffset(_position.getXOffset() + (mousePosition.x - _previousMousePosition.x) * scalarX, _position.getYOffset() + (mousePosition.y - _previousMousePosition.y) * scalarY);
    _previousMousePosition = mousePosition;
  }

  @Override
  public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
    float factor = amountY > 0 ? POSITIVE_ZOOM_FACTOR : NEGATIVE_ZOOM_FACTOR;

    if(_isCtrlPressed) {
      _position.setScale(_position.getXScale() * factor, _position.getYScale());
      return true;
    }

    if(_isShiftPressed) {
      _position.setScale(_position.getXScale(), _position.getYScale() * factor);
      return true;
    }

    _position.setScale(_position.getXScale() * factor, _position.getYScale() * factor);
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
