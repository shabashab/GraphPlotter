package com.shabashab.graphplotter.ui.elements;

import com.shabashab.graphplotter.ui.GuiElementsPool;
import imgui.ImGui;

public abstract class ImGuiWindow extends ImGuiRenderable {
  protected final String title;

  protected ImGuiWindow(GuiElementsPool pool, String title) {
    super(pool);
    this.title = title;
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
