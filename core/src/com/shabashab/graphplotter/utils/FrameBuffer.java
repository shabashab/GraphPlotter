package com.shabashab.graphplotter.utils;

import com.badlogic.gdx.utils.Disposable;
import org.lwjgl.opengl.GL30;

public class FrameBuffer implements Disposable {
  private final int _framebufferId;

  public FrameBuffer() {
    _framebufferId = GL30.glGenFramebuffers();
  }

  public void bind() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, _framebufferId);
  }

  public void unbind() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
  }

  @Override
  public void dispose() {
    GL30.glDeleteFramebuffers(_framebufferId);
  }
}
