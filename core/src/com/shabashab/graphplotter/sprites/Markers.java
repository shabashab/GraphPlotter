package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.utils.GraphPosition;

public class Markers extends Sprite implements Disposable {
  private final GraphPosition _position;
  private final Viewport _viewport;
  private final SpriteBatch _batch;
  private final ShapeRenderer _renderer;
  private final Camera _camera;

  private final BitmapFont _font;

  public Markers(GraphPosition position) {
    _position = position;

    _camera = new OrthographicCamera();
    _viewport = new ScreenViewport(_camera);

    _batch = new SpriteBatch();
    _renderer = new ShapeRenderer();

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter generatorParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    generatorParameter.size = 12;
    generatorParameter.flip = true;

    _font = generator.generateFont(generatorParameter);

    generator.dispose();
  }

  @Override
  public void draw(Batch batch) {
    _viewport.update((int)getWidth(), (int)getHeight(), true);
    _viewport.setScreenPosition(0, (int)getY());
    _viewport.apply();

    _batch.setProjectionMatrix(_viewport.getCamera().combined);
    _camera.update();

    float xAxisY = ((getHeight() / 2f)) + ((_position.getYOffset() / _position.getYScale() / 2) * getHeight() * -1);
    float yAxisX = ((getWidth() / 2f)) + ((_position.getXOffset() / _position.getXScale() / 2) * getWidth());

    final float lineHeight = 6f;
    final int marksPerScreenWidth = 10;

    _batch.setProjectionMatrix(_viewport.getCamera().combined);

    _renderer.setProjectionMatrix(_viewport.getCamera().combined);

    float stepX = _position.getXScale() / marksPerScreenWidth;

    float pixelsPerUnitX = getWidth() / _position.getXScale() / 2;

    float leftSideGraphX = ((-1 * (getWidth() / 2)) / pixelsPerUnitX) - _position.getXOffset();
    float rightSideGraphX = ((getWidth() / 2) / pixelsPerUnitX) - _position.getXOffset();
    int xStartI = (int)Math.floor(leftSideGraphX / stepX) - 1;
    int xEndI = (int)Math.floor(rightSideGraphX / stepX) + 1;

    _renderer.begin(ShapeRenderer.ShapeType.Line);

    for(int i = xStartI; i <= xEndI; i++) {
      float graphX = stepX * i;
      float realOffsetX = _position.getXOffset() * pixelsPerUnitX;
      float realX = ((graphX * pixelsPerUnitX) + getWidth() / 2) + realOffsetX;

      if(graphX == 0)
        continue;

      _renderer.line(realX, xAxisY - lineHeight, realX, xAxisY + lineHeight);
      _renderer.end();
      _batch.begin();
      _font.draw(_batch, String.format("%.2f", graphX), realX, xAxisY + lineHeight + 7);
      _batch.end();
      _renderer.begin(ShapeRenderer.ShapeType.Line);
//      _font.draw(_batch, "dd", realX, xAxisY - lineHeight);
    }

    float stepY = _position.getYScale() / marksPerScreenWidth;
    float pixelsPerUnitY = getHeight() / _position.getYScale() / 2;

    float topSideGraphY = (((getHeight() / 2)) / pixelsPerUnitY) + _position.getYOffset();
    float bottomSideGraphY = ((-1 * getHeight() / 2) / pixelsPerUnitY) + _position.getYOffset();
    int yEndI = (int)Math.floor(topSideGraphY / stepY) + 1;
    int yStartI = (int)Math.floor(bottomSideGraphY / stepY) - 1;

    for(int i = yStartI; i <= yEndI; i++) {
      float graphY = stepY * i;
      float realOffsetY = _position.getYOffset() * pixelsPerUnitY * -1;
      float realY = ((graphY * pixelsPerUnitY) + getHeight() / 2) + realOffsetY;

      if(graphY == 0)
        continue;

      _renderer.line(yAxisX - lineHeight, realY, yAxisX, realY);
      _renderer.end();
      _batch.begin();
      _font.draw(_batch, String.format("%.2f", graphY * -1), yAxisX + lineHeight, realY);
      _batch.end();
      _renderer.begin(ShapeRenderer.ShapeType.Line);
//      _font.draw(_batch, "dd", realX, xAxisY - lineHeight);
    }

    _batch.begin();
    _font.draw(_batch, "0", yAxisX + lineHeight, xAxisY + lineHeight);
    _batch.end();

    _renderer.end();
  }

  @Override
  public void dispose() {

  }
}
