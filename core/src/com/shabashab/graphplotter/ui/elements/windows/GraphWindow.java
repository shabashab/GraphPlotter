package com.shabashab.graphplotter.ui.elements.windows;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.ui.GuiElementsPool;
import com.shabashab.graphplotter.ui.elements.base.ImGuiWindow;
import com.shabashab.graphplotter.utils.StageFramebufferRenderer;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;

public class GraphWindow extends ImGuiWindow {
  private final GraphActor _graphActor;

  private boolean _isInFocus = false;

  private float _windowPositionX = 0;
  private float _windowPositionY = 0;

  private float _windowWidth = 0;
  private float _windowHeight = 0;

  private final StageFramebufferRenderer _graphStageRenderer;

  public GraphWindow(GuiElementsPool pool) {
    super(pool, "Plot");

    Stage graphStage = new Stage();

    _graphActor = new GraphActor(new Vector2[0]);
    graphStage.addActor(_graphActor);

    _graphActor.setGraphTitle("R(a) = sin(0.99a)");
    _graphActor.setDisplayGraphTitle(true);

    _graphStageRenderer = new StageFramebufferRenderer(graphStage);
  }

  public boolean getIsInFocus() {
    return _isInFocus;
  }

  @Override
  protected void setup() {
    _isInFocus = ImGui.isWindowFocused();

    int width = (int) ImGui.getWindowSizeX();
    int height = (int) ImGui.getWindowSizeY();

    float x = ImGui.getWindowPosX();
    float y = ImGui.getWindowPosY();

    if ((_graphStageRenderer.getTextureWidth() != width) || (_graphStageRenderer.getTextureHeight() != height)) {
      onWindowSizeUpdate(width, height);
    }

    if ((_windowPositionX != x) || (_windowPositionY != y)) {
      onWindowPositionUpdate(x, y);
    }

    _graphStageRenderer.render();

    int textureId = _graphStageRenderer.getTextureId();

    ImGui.getWindowDrawList().addImage(
            textureId,
            ImGui.getCursorScreenPosX(),
            ImGui.getCursorScreenPosY(),
            ImGui.getWindowPosX() + ImGui.getWindowSizeX(),
            ImGui.getWindowPosY() + ImGui.getWindowSizeY()
    );
  }

  public float getWidth() {
    return _windowWidth;
  }

  public float getHeight() {
    return _windowHeight;
  }

  public float getX() {
    return _windowPositionX;
  }

  public float getY() {
    return _windowPositionY;
  }

  public GraphActor getGraphActor() {
    return _graphActor;
  }

  private void onWindowSizeUpdate(float width, float height) {
    _graphActor.setSize(width, height);
    _graphStageRenderer.updateTextureSize((int)width, (int)height);

    _windowWidth = width;
    _windowHeight = height;
  }

  private void onWindowPositionUpdate(float x, float y) {
    _windowPositionX = x;
    _windowPositionY = y;
  }

  @Override
  protected void beforeBegin() {
    float windowSizeX = ImGui.getMainViewport().getWorkSizeX();
    float windowSizeY = ImGui.getMainViewport().getWorkSizeY();

    float graphWindowSize = windowSizeY / 1.5f;

    setSize(graphWindowSize, graphWindowSize, ImGuiCond.FirstUseEver);
    setPosition((windowSizeX / 2) - (graphWindowSize / 2), (windowSizeY / 2) - (graphWindowSize / 2), ImGuiCond.FirstUseEver);

    super.beforeBegin();

    ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
  }

  @Override
  protected void afterEnd(boolean beenRendered) {
    ImGui.popStyleVar();
  }
}
