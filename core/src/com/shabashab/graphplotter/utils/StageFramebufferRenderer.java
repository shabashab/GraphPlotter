package com.shabashab.graphplotter.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.lwjgl.opengl.GL30;

public class StageFramebufferRenderer extends FramebufferRenderer {
  private final Stage _stage;

  public StageFramebufferRenderer(Stage stage) {
    _stage = stage;
  }

  public StageFramebufferRenderer(Stage stage, int width, int height) {
    super(width, height);
    _stage = stage;
  }

  @Override
  protected void draw() {
    GL30.glViewport(0, 0, getTextureWidth(), getTextureHeight());

    GL30.glClearColor(0f, 0f, 0f, 1f);
    GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);

    _stage.draw();
  }
}
