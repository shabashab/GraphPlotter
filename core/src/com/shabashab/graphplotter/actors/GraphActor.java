package com.shabashab.graphplotter.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Disposable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphActor extends Actor implements Disposable {
  private final ShapeRenderer _renderer;

  private Mesh _graphMesh;
  private final Mesh _axisMesh;

  private Vector2 _scale;

  private final ShaderProgram _graphShader;
  private final ShaderProgram _axisShader;

  private Vector2 _offset;

  private ShaderProgram loadShader(String vertexShaderPath, String fragmentShaderPath) {
    ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vertexShaderPath), Gdx.files.internal(fragmentShaderPath));

    if (!shader.isCompiled()) {
      throw new IllegalStateException("Failed to compile shader! " + _graphShader.getLog());
    }

    return shader;
  }

  public GraphActor() {
    _renderer = new ShapeRenderer();

    _graphShader = loadShader("shaders/graph.vert", "shaders/graph.frag");
    _axisShader = loadShader("shaders/axis.vert", "shaders/axis.frag");

    _axisMesh = createAxisMesh();

    _offset = new Vector2(0f, 0f);

    this.setBounds(getX(), getY(), getWidth(), getHeight());

    this.addListener(new InputListener() {
      private Vector2 _previousMousePosition;

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        _previousMousePosition = new Vector2(x, y);
        return true;
      }

      @Override
      public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float scalarX = _scale.x / getWidth();
        float scalarY = _scale.y / getHeight();

        Vector2 mousePosition = new Vector2(x, y);

        _offset.set(_offset.x + (mousePosition.x - _previousMousePosition.x) * scalarX, _offset.y + (mousePosition.y - _previousMousePosition.y) * scalarY);
        _previousMousePosition = mousePosition;
      }

      @Override
      public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
        float multiplier = (amountY * 0.75f) + 1.25f;

        _scale.set(_scale.x * multiplier, _scale.y * multiplier);

        return true;
      }
    });

    _scale = new Vector2(10.f, 1.f);
  }

  public Vector2 getOffset() {
    return this._offset;
  }

  public void setOffset(Vector2 offset) {
    this._offset = offset;
  }

  private Mesh createAxisMesh() {
    MeshBuilder builder = new MeshBuilder();

    VertexAttributes attributes = new VertexAttributes(VertexAttribute.Position());

    builder.begin(attributes, GL20.GL_LINES);

    builder.line(-1f, 0f, 0f, 1f, 0f, 0f);
    builder.line(0f, -1f, 0f, 0f, 1f, 0f);

    return builder.end();
  }

  private float[] createVertices(Vector2[] points) {
    float[] vertices = new float[points.length * 2];

    for (int i = 0; i < points.length; i++) {
      vertices[i * 2] = points[i].x;
      vertices[(i * 2) + 1] = points[i].y;
    }

    return vertices;
  }

  public void setPoints(Vector2[] points) {
    if (_graphMesh != null)
      _graphMesh.dispose();

    _graphMesh = new Mesh(true, points.length, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

    float[] vertices = createVertices(points);

    _graphMesh.setVertices(vertices, 0, vertices.length);

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    float[] offsetUniformData = new float[] {_offset.x, _offset.y};
    float[] scaleUniformData = new float[] {_scale.x, _scale.y};

    if (_graphMesh != null) {
      _graphShader.bind();

      _graphShader.setUniform2fv("u_offset", offsetUniformData, 0, 2);
      _graphShader.setUniform2fv("u_scale", scaleUniformData, 0, 2);

      Gdx.gl.glLineWidth(1.0f);
      _graphMesh.render(_graphShader, GL20.GL_LINE_STRIP);

      System.out.print(_graphShader.getLog());
    }

    _axisShader.bind();
    _axisShader.setUniform2fv("u_offset", offsetUniformData, 0, 2);
    _graphShader.setUniform2fv("u_scale", scaleUniformData, 0, 2);

    _axisMesh.render(_axisShader, GL20.GL_LINES);

    Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
  }

  @Override
  public void dispose() {
    _renderer.dispose();
    _axisShader.dispose();
    _graphShader.dispose();
    _axisMesh.dispose();
    _graphMesh.dispose();
  }
}
