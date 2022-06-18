package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;
import org.lwjgl.opengl.GL20;

public class AxisArrows extends MeshSprite {
  private final float[] _screenSize;
  private final float[] _arrowsSize;
  private final GraphPosition _position;

  public AxisArrows(GraphPosition position, Vector2 size) {
    super(GL20.GL_TRIANGLES);

    _position = position;
    _arrowsSize = new float[]{size.x, size.y};
    _screenSize = new float[]{0, 0};
  }

  @Override
  protected Mesh createMesh() {
    Mesh result = new Mesh(true, 6, 0, new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_side"), new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_component"));

    float[] vertices = new float[]{
            1f, 0f,
            1f, 1f,
            1f, 2f,

            2f, 0f,
            2f, 1f,
            2f, 2f
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
    return ShaderLoader.loadShader("shaders/arrows.vert", "shaders/white.frag");
  }

  @Override
  protected void setUniforms(ShaderProgram shader) {
    shader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    shader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);
    shader.setUniform2fv("u_arrow_size", _arrowsSize, 0, 2);

    shader.setUniform2fv("u_screenSize", _screenSize, 0, 2);
    shader.setUniform2fv("u_position", new float[]{getX(), getY()}, 0, 2);
    shader.setUniform2fv("u_size", new float[]{getWidth(), getHeight()}, 0, 2);
  }
}
