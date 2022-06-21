package com.shabashab.graphplotter.ui.elements.base;

import com.badlogic.gdx.math.Vector2;
import com.shabashab.graphplotter.ui.GuiElementsPool;
import imgui.ImGui;

public abstract class ImGuiWindow extends ImGuiRenderable {
  protected final String title;

  private Vector2 _position;
  private Vector2 _size;

  private int _positionCond;
  private int _sizeCond;

  protected ImGuiWindow(GuiElementsPool pool, String title) {
    super(pool);
    this.title = title;
  }

  public void setPosition(float x, float y, int condition) {
    _positionCond = condition;

    if(_position == null) {
      _position = new Vector2(x, y);
      return;
    }

    _position.set(x, y);
  }

  public void setSize(float width, float height, int condition) {
    _sizeCond = condition;

    if(_size == null) {
      _size = new Vector2(width, height);
      return;
    }

    _size.set(width, height);
  }

  @Override
  protected void beforeBegin() {
    if(_position != null)
      ImGui.setNextWindowPos(_position.x, _position.y, _positionCond);

    if(_size != null)
      ImGui.setNextWindowSize(_size.x, _size.y, _sizeCond);
  }

  @Override
  protected boolean begin() {
    return ImGui.begin(title);
  }

  @Override
  protected void end() {
    ImGui.end();
  }
}
