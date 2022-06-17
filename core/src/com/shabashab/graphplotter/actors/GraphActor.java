package com.shabashab.graphplotter.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.shabashab.graphplotter.sprites.Axis;
import com.shabashab.graphplotter.sprites.Plot;
import com.shabashab.graphplotter.utils.GraphPosition;

public class GraphActor extends Actor implements Disposable {
  private final Axis _axis;
  private final Plot _plot;

  private final GraphPosition _position;

  public GraphActor(Vector2[] points) {
    _position = new GraphPosition(0, 0, 10, 10);

    _axis = new Axis(_position);
    _plot = new Plot(points, _position);

    _plot.setLineWidth(1.5f);
  }

  public GraphPosition getPosition() {
    return _position;
  }

  public void updatePoints(Vector2[] points) {
    this._plot.updatePoints(points);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    _axis.setSize(getWidth(), getHeight());
    _plot.setSize(getWidth(), getHeight());

    _axis.draw(batch);
    _plot.draw(batch);
  }

  @Override
  public void dispose() {
    _axis.dispose();
    _plot.dispose();
  }
}
