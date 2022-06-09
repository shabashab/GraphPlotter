package com.shabashab.graphplotter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderLoader {
  public static ShaderProgram loadShader(String vertexPath, String fragmentPath) {
    ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vertexPath), Gdx.files.internal(fragmentPath));

    if (!shader.isCompiled()) {
      throw new IllegalStateException("Failed to compile shader! " + shader.getLog());
    }

    return shader;
  }
}
