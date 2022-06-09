package com.shabashab.graphplotter.utils;

public class GraphPosition {

  private final float[] _offset;
  private final float[] _scale;


  public GraphPosition(float xOffset, float yOffset, float xScale, float yScale) {
    _offset = new float[] {xOffset, yOffset};
    _scale = new float[] {xScale, yScale};
  }

  public float getXOffset() {
    return _offset[0];
  }

  public float getYOffset() {
    return _offset[1];
  }

  public void setOffset(float x, float y) {
    _offset[0] = x;
    _offset[1] = y;
  }

  public float getXScale() {
    return _scale[0];
  }

  public float getYScale() {
    return _scale[1];
  }

  public void setScale(float x, float y) {
    _scale[0] = x;
    _scale[1] = y;
  }

  public float[] getScaleUniformData() {
    return _scale;
  }

  public float[] getOffsetUniformData() {
    return _offset;
  }
}
