package com.shabashab.graphplotter.ui;

import imgui.ImGui;
import imgui.flag.ImGuiCond;

public abstract class ImGuiWindow {
  protected final String title;

  private float _width;
  private float _height;

  protected ImGuiWindow(String title) {
    this.title = title;
  }

  public void setSize(float width, float height) {
    _width = width;
    _height = height;
  }

  public void draw() {
    ImGui.setNextWindowSize(_width, _height, ImGuiCond.FirstUseEver);
    ImGui.begin(title);
    setupWindow();
    ImGui.end();
  }

  protected abstract void setupWindow();
}
