package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.shabashab.graphplotter.utils.GraphPosition;
import com.shabashab.graphplotter.utils.ShaderLoader;

public class Axis extends Sprite implements Disposable {
  private final GraphPosition _position;


  private final ShaderProgram _axisShader;
  private final ShaderProgram _arrowsShader;

  private final Mesh _axisMesh;
  private final Mesh _arrowsMesh;

  public Axis(GraphPosition position) {
    _position = position;

    _axisShader = ShaderLoader.loadShader("shaders/axis.vert", "shaders/axis.frag");
    _arrowsShader = ShaderLoader.loadShader("shaders/arrows.vert", "shaders/white.frag");

    _axisMesh = createAxisMesh();
    _arrowsMesh = createArrowsMesh();
  }

  private Mesh createAxisMesh() {
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


  private Mesh createArrowsMesh() {
    //a_side defines which side arrow sticks to. Side 1 - right side, side 2 - top side
    //a_component defines the number of vertex for the arrow (there are 3 components)

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

  public GraphPosition getPosition() {
    return _position;
  }

  @Override
  public void draw(Batch batch) {
    _axisShader.bind();

    _axisShader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    _axisShader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);

    _axisMesh.render(_axisShader, GL20.GL_LINES);

    _arrowsShader.bind();

    _arrowsShader.setUniform2fv("u_offset", _position.getOffsetUniformData(), 0, 2);
    _arrowsShader.setUniform2fv("u_scale", _position.getScaleUniformData(), 0, 2);
    _arrowsShader.setUniform2fv("u_size", new float[]{this.getWidth(), this.getHeight()}, 0, 2);

    //First component of vector defines the length of the arrow, second component - the boldness of the arrow
    _arrowsShader.setUniform2fv("u_arrow_size", new float[]{30, 20}, 0, 2);

    _arrowsMesh.render(_arrowsShader, GL20.GL_TRIANGLES);
  }

  @Override
  public void dispose() {
    _axisShader.dispose();
    _axisMesh.dispose();
  }
}
