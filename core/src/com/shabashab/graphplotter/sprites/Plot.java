package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;

public class Plot extends Sprite implements Disposable {
  private Mesh _mesh;

  private final GraphPosition _position;

  private final ShaderProgram _shader;

  private float _lineWidth;

  public Plot(Vector2[] points, GraphPosition position) {
    _mesh = createMeshFromPoints(points);
    _position = position;
    _shader = ShaderLoader.loadShader("shaders/plot.vert", "shaders/plot.frag");
    _lineWidth = 1.0f;
  }

  public GraphPosition getPosition() {
    return _position;
  }

  public float getLineWidth() {
    return this._lineWidth;
  }

  public void setLineWidth(float value) {
    this._lineWidth = value;
  }

  public void updatePoints(Vector2[] points) {
    _mesh.dispose();
    _mesh = createMeshFromPoints(points);
  }

  private float[] createVerticesFromPoints(Vector2[] points) {
    float[] vertices = new float[points.length * 2];

    for (int i = 0; i < points.length; i++) {
      vertices[i * 2] = points[i].x;
      vertices[(i * 2) + 1] = points[i].y;
    }

    return vertices;
  }

  private Mesh createMeshFromPoints(Vector2[] points) {
    float[] vertices = createVerticesFromPoints(points);

    Mesh result = new Mesh(true, points.length, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
    result.setVertices(vertices, 0, vertices.length);

    return result;
  }

  @Override
  public void draw(Batch batch) {
    _shader.bind();

    _shader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    _shader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);

    Gdx.gl.glLineWidth(_lineWidth);
    _mesh.render(_shader, GL20.GL_LINE_STRIP);
  }

  @Override
  public void dispose() {
    _shader.dispose();
    _mesh.dispose();
  }
}
