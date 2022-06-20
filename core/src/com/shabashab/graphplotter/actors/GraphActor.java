package com.shabashab.graphplotter.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.sprites.Axis;
import com.shabashab.graphplotter.sprites.AxisArrows;
import com.shabashab.graphplotter.sprites.Markers;
import com.shabashab.graphplotter.sprites.Plot;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;

public class GraphActor extends Actor implements Disposable {
  private final Axis _axis;
  private final AxisArrows _arrows;
  private final Plot _plot;
  private final Markers _markers;

  private final GraphPosition _position;

  private final SpriteBatch _batch;
  private final ShaderProgram _batchShader;
  private final BitmapFont _font;
  private final Viewport _viewport;
  private final Camera _camera;
  private final GlyphLayout _glyphLayout;

  private boolean _displayGraphTitle;
  private String _graphTitle;

  private Vector2[] _points;

  public GraphActor(Vector2[] points) {
    this(points, new GraphPosition(0, 0, 1.2f, 1.2f));
  }

  public GraphActor(Vector2[] points, GraphPosition position) {
    _position = position;
    _points = points;

    _axis = new Axis(_position);
    _arrows = new AxisArrows(_position, new Vector2(30, 20));
    _plot = new Plot(points, _position);
    _markers = new Markers(_position);

    _plot.setLineWidth(1f);

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter generatorParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    generatorParameter.size = 60;
    generatorParameter.flip = true;

    _font = generator.generateFont(generatorParameter);

    _glyphLayout = new GlyphLayout();

    generator.dispose();

    _batch = new SpriteBatch();
    _batchShader = ShaderLoader.loadShader("shaders/titleText.vert", "shaders/titleText.frag");

    _camera = new OrthographicCamera();
    _viewport = new ScreenViewport(_camera);

    _displayGraphTitle = false;
    _graphTitle = "";
  }

  public GraphPosition getPosition() {
    return _position;
  }

  public void updatePoints(Vector2[] points) {
    this._plot.updatePoints(points);
    _points = points;
  }

  public void setGraphTitle(String title) {
    _graphTitle = title;
  }

  public void setDisplayGraphTitle(boolean displayTitle) {
    _displayGraphTitle = displayTitle;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    float graphYOffset = 0f;
    float textX = 0, textY = 0;

    if(_displayGraphTitle) {
      _glyphLayout.setText(_font, _graphTitle);

      _viewport.update((int)getWidth(), (int)getHeight(), true);
      _camera.update();

      _batch.setProjectionMatrix(_camera.combined);
      _batch.setShader(_batchShader);

      float width = _glyphLayout.width;
      float height = _glyphLayout.height;

      float x = (getWidth() / 2) - (width / 2);
      float y = 30;

      graphYOffset = y + height;

      textX = x;
      textY = y;
    }

    if(graphYOffset > 0) {
      graphYOffset += 30;
    }

    _axis.setSize(getWidth(), getHeight() - graphYOffset);
    _arrows.setSize(getWidth(), getHeight() - graphYOffset);
    _plot.setSize(getWidth(), getHeight() - graphYOffset);
    _markers.setSize(getWidth(), getHeight() - graphYOffset);

    _axis.setPosition(0, graphYOffset);
    _arrows.setPosition(0, graphYOffset);
    _plot.setPosition(0, graphYOffset);
    _markers.setPosition(0, graphYOffset);

    _axis.setScreenSize(getWidth(), getHeight());
    _arrows.setScreenSize(getWidth(), getHeight());
    _plot.setScreenSize(getWidth(), getHeight());

    _axis.draw(batch);
    _arrows.draw(batch);
    _plot.draw(batch);
    _markers.draw(batch);

    if(_displayGraphTitle) {
      _viewport.apply();
      _batch.begin();
      _font.draw(batch, _graphTitle, textX, textY);
      _batch.end();
    }
  }

  public String getGraphTitle() {
    return _graphTitle;
  }

  public GraphActor createCopy() {
    return new GraphActor(_points, _position);
  }

  @Override
  public void dispose() {
    _axis.dispose();
    _plot.dispose();
  }
}
