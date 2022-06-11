package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.input.UIEventListener;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGuiWindow;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class UIActor extends Actor {
  private final Stage _graphStage;
  private final GraphActor _graphActor;
  private final GraphPosition _graphPosition;

  private int _textureId;
  private final int _frameBufferId;

  private int _graphTextureWidth = 0;
  private int _graphTextureHeight = 0;

  private float _graphWindowPositionX = 0;
  private float _graphWindowPositionY = 0;

  private final UIEventListener _eventListener;

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

  public UIActor() {
    ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

    _graphStage = new Stage();

    _graphActor = new GraphActor(calculatePoints(-10f, 10f, 200));

    _graphStage.addActor(_graphActor);

    _graphPosition = _graphActor.getPosition();

    _eventListener = new UIEventListener(_graphActor.getInputListener(), new Vector2(0, 0), new Vector2(0, 0));
    addListener(_eventListener);

    _frameBufferId = GL30.glGenFramebuffers();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    ImGuiHelper.getImGuiGlfw().newFrame();

    ImGui.newFrame();

    ImGui.dockSpaceOverViewport(ImGui.getMainViewport());

    ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);

    if(ImGui.beginMainMenuBar()) {
      if(ImGui.beginMenu("File")) {
        if(ImGui.menuItem("Exit")) {
          System.exit(0);
        }
        ImGui.endMenu();
      }
      ImGui.endMainMenuBar();
    }

    ImGui.setNextWindowSize(600, 600, ImGuiCond.FirstUseEver);

    if (ImGui.begin("Plot")) {
      int width = (int) ImGui.getWindowWidth();
      int height = (int) ImGui.getWindowHeight();

      float x = ImGui.getWindowPosX();
      float y = ImGui.getWindowPosY();

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, _frameBufferId);

      if ((_graphTextureWidth != width) || (_graphTextureHeight != height)) {
        GL30.glDeleteTextures(_textureId);

        _textureId = GL30.glGenTextures();

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, _textureId);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGB, width, height, 0, GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE, (ByteBuffer) null);

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, _textureId, 0);

        _graphTextureWidth = width;
        _graphTextureHeight = height;

        _graphStage.getViewport().update(_graphTextureWidth, _graphTextureHeight, true);
        _graphStage.getCamera().update(true);

        _graphActor.setSize(width - 40, height - 40);
        _eventListener.updateGraphViewSize(width - 40, height - 40);
      }

      if((_graphWindowPositionX != x) || (_graphWindowPositionY != y)) {
        _eventListener.updateGraphViewPos(x + 20, y + 20);

        _graphWindowPositionX = x;
        _graphWindowPositionY = y;
      }

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, _frameBufferId);

      GL30.glViewport(0, 0, width, height);

      GL30.glClearColor(0f, 0f, 0f, 1f);
      GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);

      _graphStage.draw();

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

      ImGui.getWindowDrawList().addImage(
              _textureId,
              ImGui.getCursorScreenPosX(),
              ImGui.getCursorScreenPosY(),
              ImGui.getWindowPosX() + ImGui.getWindowSizeX(),
              ImGui.getWindowPosY() + ImGui.getWindowSizeY()
      );

      ImGui.end();
    }

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }
}
