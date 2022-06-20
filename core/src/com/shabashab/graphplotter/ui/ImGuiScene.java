package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.shabashab.graphplotter.ui.elements.ImGuiRenderable;
import imgui.ImGui;

import java.util.ArrayList;

public abstract class ImGuiScene extends Actor {
  private final ArrayList<ImGuiRenderable> _renderable;

  protected void addToRenderQueue(ImGuiRenderable renderable) {
    _renderable.add(renderable);
  }

  public ImGuiScene() {
    _renderable = new ArrayList<>();

    create();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    beforeNewFrame();

    ImGuiHelper.getImGuiGlfw().newFrame();
    ImGui.newFrame();

    beforeQueueRender();

    renderQueue();

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }

  protected abstract void create();
  protected void beforeNewFrame() {}

  protected void beforeQueueRender() {}

  private void renderQueue() {
    for(ImGuiRenderable renderable: _renderable) {
      renderable.render();
    }
  }
}
