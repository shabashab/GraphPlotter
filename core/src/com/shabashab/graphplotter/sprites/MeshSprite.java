package com.shabashab.graphplotter.sprites;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public abstract class MeshSprite extends Sprite implements Disposable {
  private final Mesh _mesh;
  private final ShaderProgram _shader;
  private final int _primitive;

  public MeshSprite(int primitiveType) {
    _mesh = createMesh();
    _shader = createShader();
    _primitive = primitiveType;
  }

  protected abstract Mesh createMesh();
  protected abstract ShaderProgram createShader();

  //Here you set all the uniforms needed for shader to execute
  protected abstract void setUniforms(ShaderProgram shader);

  protected ShaderProgram getShader() {
    return _shader;
  }

  @Override
  public void draw(Batch batch) {
    _shader.bind();
    setUniforms(_shader);

    _mesh.render(_shader, _primitive);
  }

  @Override
  public void dispose() {
    _mesh.dispose();
    _shader.dispose();
  }
}