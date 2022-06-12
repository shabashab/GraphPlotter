package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.ui.elements.ImGuiWindow;
import com.shabashab.graphplotter.utils.StageFramebufferRenderer;
import imgui.ImGui;

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

    _graphActor = new GraphActor(calculatePoints(-10f, 10f, 200));
    graphStage.addActor(_graphActor);

    _graphStageRenderer = new StageFramebufferRenderer(graphStage);
  }


  private Vector2[] calculatePoints(float from, float to, int stepsCount) {
    float stepSize = (to - from) / stepsCount;

    Vector2[] points = new Vector2[stepsCount];

    for (int i = 0; i < stepsCount; i++) {
      float x = from + stepSize * i;
      float y = (float) Math.sin(x);

      points[i] = new Vector2(x, y);
    }

    return points;
  }

  public boolean getIsInFocus() {
    return _isInFocus;
  }

  @Override
  protected void setup() {
    _isInFocus = ImGui.isWindowFocused();

    int width = (int) ImGui.getWindowWidth();
    int height = (int) ImGui.getWindowHeight();

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
    _graphActor.setSize(width - 40, height - 40);
    _graphStageRenderer.updateTextureSize((int)width, (int)height);

    _windowWidth = width;
    _windowHeight = height;
  }

  private void onWindowPositionUpdate(float x, float y) {
    _windowPositionX = x;
    _windowPositionY = y;
  }
}
