package com.shabashab.graphplotter.ui.elements.base;

import com.shabashab.graphplotter.ui.GuiElementsPool;
import imgui.ImGui;

public abstract class ImGuiMainMenuBar extends ImGuiRenderable {
  public ImGuiMainMenuBar(GuiElementsPool elementsPool) {
    super(elementsPool);
  }

  @Override
  protected boolean begin() {
    return ImGui.beginMainMenuBar();
  }

  @Override
  protected void end() {
    ImGui.endMainMenuBar();
  }
}
