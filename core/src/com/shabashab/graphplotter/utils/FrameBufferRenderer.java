package com.shabashab.graphplotter.utils;

import com.badlogic.gdx.utils.Disposable;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public abstract class FrameBufferRenderer implements Disposable {
  private int _textureId = 0;

  private final FrameBuffer _frameBuffer;

  private int _textureWidth;
  private int _textureHeight;

  private boolean _textureGenerated = false;

  protected FrameBufferRenderer() {
    _frameBuffer = new FrameBuffer();
  }

  protected FrameBufferRenderer(int width, int height) {
    this();
    _textureId = createTextureWithSize(width, height);
  }

  public int createTextureWithSize(int width, int height) {
    int textureId = GL30.glGenTextures();

    GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);

    GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGB, width, height, 0, GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE, (ByteBuffer) null);

    GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
    GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

    GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

    _textureWidth = width;
    _textureHeight = height;

    return textureId;
  }

  public int getTextureWidth() {
    return _textureWidth;
  }

  public int getTextureHeight() {
    return _textureHeight;
  }

  public int getTextureId() {
    return _textureId;
  }

  public void updateTextureSize(int width, int height) {
    if(_textureGenerated) {
      GL30.glDeleteTextures(_textureId);
    }

    _textureId = createTextureWithSize(width, height);

    _frameBuffer.bind();
    GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, _textureId, 0);
    _frameBuffer.unbind();

    _textureGenerated = true;
  }

  public void render() {
    if(!_textureGenerated) {
      throw new IllegalStateException("Texture must be generated before rendering to framebuffer. Use updateTextureSize() to generate the texture.");
    }

    _frameBuffer.bind();

    draw();

    _frameBuffer.unbind();
  }

  protected abstract void draw();

  public FrameBuffer getFrameBuffer() {
    return _frameBuffer;
  }

  @Override
  public void dispose() {
    _frameBuffer.dispose();
  }
}
