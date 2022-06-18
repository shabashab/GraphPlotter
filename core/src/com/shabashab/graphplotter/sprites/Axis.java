package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;

public class Axis extends MeshSprite {
  private final GraphPosition _position;

  private final float[] _screenSize;

  public Axis(GraphPosition position) {
    super(GL20.GL_LINES);

    _position = position;
    _screenSize = new float[2];
  }

  public GraphPosition getPosition() {
    return _position;
  }

  @Override
  protected Mesh createMesh() {
    Mesh result = new Mesh(true, 4, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

    float[] vertices = new float[]{
            -1f, 0f,
            1f, 0f,

            0f, -1f,
            0f, 1f
    };

    result.setVertices(vertices, 0, vertices.length);

    return result;
  }

  public void setScreenSize(float width, float height) {
    _screenSize[0] = width;
    _screenSize[1] = height;
  }

  @Override
  protected ShaderProgram createShader() {
    return ShaderLoader.loadShader("shaders/axis.vert", "shaders/axis.frag");
  }

  @Override
  protected void setUniforms(ShaderProgram shader) {
    shader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    shader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);
    shader.setUniform2fv("u_screenSize", _screenSize, 0, 2);
    shader.setUniform2fv("u_position", new float[] {getX(), getY()}, 0, 2);
    shader.setUniform2fv("u_size", new float[] {getWidth(), getHeight()}, 0, 2);
  }
}
