package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.input.UIEventListener;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
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

  private final UIEventListener _eventListener;

  private Vector2[] calculatePoints(float from, float to, int stepsCount) {
    float stepSize = (to - from) / stepsCount;

    Vector2[] points = new Vector2[stepsCount];

    for(int i = 0; i < stepsCount; i++) {
      float x = from + stepSize * i;
      float y = (float) Math.sin(x);

      points[i] = new Vector2(x, y);
    }

    return points;
  }

  public UIActor() {
    Camera camera = new OrthographicCamera(500, 500);
    Viewport viewport = new ExtendViewport(500, 500, camera);

    _graphStage = new Stage();

    _graphStage.setViewport(viewport);

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

    float mainMenuBarHeight = 0;

    if(ImGui.beginMainMenuBar()) {
      if(ImGui.beginMenu("File")) {
        ImGui.menuItem("Save to file...");
        ImGui.endMenu();
      }

      mainMenuBarHeight = ImGui.getFrameHeight();

      ImGui.endMainMenuBar();
    }

    float leftWindowWidth = Gdx.graphics.getWidth() / 4f;

    ImGui.setNextWindowPos(0, mainMenuBarHeight);
    ImGui.setNextWindowSize(leftWindowWidth, Gdx.graphics.getHeight() - mainMenuBarHeight);

    if(ImGui.begin("Parameters")) {
      ImGui.text("Graph offset x: " + _graphPosition.getXOffset());
      ImGui.text("Graph offset y: " + _graphPosition.getYOffset());
      ImGui.text("Graph scale x: " + _graphPosition.getXScale());
      ImGui.text("Graph scale y: " + _graphPosition.getYScale());

      ImGui.text("Graph actor x: " + _graphActor.getX());
      ImGui.text("Graph actor y: " + _graphActor.getY());

      ImGui.end();
    }

    ImGui.setNextWindowPos(leftWindowWidth, mainMenuBarHeight);
    ImGui.setNextWindowSize(Gdx.graphics.getWidth() - leftWindowWidth, Gdx.graphics.getHeight() - mainMenuBarHeight);

    if(ImGui.begin("Plot")) {
      int innerWidth = (int)ImGui.getWindowWidth();
      int innerHeight = (int)ImGui.getWindowHeight();

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, _frameBufferId);

      if((_graphTextureWidth != innerWidth) || (_graphTextureHeight != innerHeight)) {
        GL30.glDeleteTextures(_textureId);

        _textureId = GL30.glGenTextures();

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, _textureId);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGB, innerWidth, innerHeight, 0, GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE, (ByteBuffer)null);

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, _textureId, 0);

        _graphTextureWidth = innerWidth;
        _graphTextureHeight = innerHeight;

        _graphStage.getViewport().update(_graphTextureWidth, _graphTextureHeight);

        float x = ImGui.getCursorScreenPosX();
        float y = ImGui.getCursorScreenPosY();

        float width = (float)innerWidth;
        float height = (float)innerHeight;

        _graphActor.setBounds(x, y, width, height);
        _eventListener.updateGraphViewBounds(x, y, width, height);
      }

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, _frameBufferId);

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

//    ImGui.showMetricsWindow();

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }
}
