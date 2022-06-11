package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;

public class Axis extends Sprite implements Disposable {
  private final ShaderProgram _shader;
  private final Mesh _mesh;
  private final GraphPosition _position;

  public Axis(GraphPosition position) {
    _position = position;
    _shader = ShaderLoader.loadShader("shaders/axis.vert", "shaders/axis.frag");
    _mesh = createMesh();
  }

  private Mesh createMesh() {
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

  public GraphPosition getPosition() {
    return _position;
  }

  @Override
  public void draw(Batch batch) {
    _shader.bind();

    _shader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    _shader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);
//    _shader.setUniformMatrix("u_projectionMatrix", , false);
    _mesh.render(_shader, GL20.GL_LINES);
  }

  @Override
  public void dispose() {
    _shader.dispose();
    _mesh.dispose();
  }
}
