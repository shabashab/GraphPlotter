package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.input.GraphWindowEventListener;
import com.shabashab.graphplotter.utils.StageFramebufferRenderer;
import imgui.ImGui;

public class GraphWindow extends ImGuiWindow {
  private final GraphActor _graphActor;

  private float _graphWindowPositionX = 0;
  private float _graphWindowPositionY = 0;

  private final GraphWindowEventListener _listener;
  private final StageFramebufferRenderer _graphStageRenderer;

  public GraphWindow() {
    super("Plot");

    Stage graphStage = new Stage();

    _graphActor = new GraphActor(calculatePoints(-10f, 10f, 200));
    graphStage.addActor(_graphActor);

    _listener = new GraphWindowEventListener(_graphActor.getInputListener(), new Vector2(0, 0), new Vector2(0, 0));

    _graphStageRenderer = new StageFramebufferRenderer(graphStage);
  }

  public EventListener getEventListener() {
    return _listener;
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

  @Override
  protected void setupWindow() {
    int width = (int) ImGui.getWindowWidth();
    int height = (int) ImGui.getWindowHeight();

    float x = ImGui.getWindowPosX();
    float y = ImGui.getWindowPosY();

    if ((_graphStageRenderer.getTextureWidth() != width) || (_graphStageRenderer.getTextureHeight() != height)) {
      onWindowSizeUpdate(width, height);
    }

    if ((_graphWindowPositionX != x) || (_graphWindowPositionY != y)) {
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

  private void onWindowSizeUpdate(float width, float height) {
    _graphActor.setSize(width - 40, height - 40);
    _listener.updateGraphViewSize(width - 40, height - 40);
    _graphStageRenderer.updateTextureSize((int)width, (int)height);
  }

  private void onWindowPositionUpdate(float x, float y) {
    _listener.updateGraphViewPos(x + 20, y + 20);

    _graphWindowPositionX = x;
    _graphWindowPositionY = y;
  }
}
