package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.utils.GraphPosition;
import imgui.ImGui;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class UIActor extends Actor {
  private Stage _graphStage;
  private GraphActor _graphActor;

  private GraphPosition _graphPosition;

  int textureId;
  int framebufferId;

  int textureWidth = 0;
  int textureHeight = 0;

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
    _graphStage = new Stage();
    _graphActor = new GraphActor(calculatePoints(-10f, 10f, 200));
    _graphActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    _graphStage.addActor(_graphActor);

    _graphPosition = _graphActor.getPosition();

    framebufferId = GL30.glGenFramebuffers();
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

    if(ImGui.begin("My left window")) {
      ImGui.text("Texture width: " + textureWidth);
      ImGui.text("Texture height: " + textureHeight);

      ImGui.text("Graph offset x: " + _graphPosition.getXOffset());
      ImGui.text("Graph offset y: " + _graphPosition.getYOffset());
      ImGui.text("Graph scale x: " + _graphPosition.getXScale());
      ImGui.text("Graph scale y: " + _graphPosition.getYScale());

      ImGui.end();
    }

    ImGui.setNextWindowPos(leftWindowWidth, mainMenuBarHeight);
    ImGui.setNextWindowSize(Gdx.graphics.getWidth() - leftWindowWidth, Gdx.graphics.getHeight() - mainMenuBarHeight);

    if(ImGui.begin("Graph window")) {
      int innerWidth = (int)ImGui.getWindowWidth();
      int innerHeight = (int)ImGui.getWindowHeight();

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferId);

      if((textureWidth != innerWidth) || (textureHeight != innerHeight)) {
        GL30.glDeleteTextures(textureId);

        textureId = GL30.glGenTextures();

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGB, innerWidth, innerHeight, 0, GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE, (ByteBuffer)null);

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, textureId, 0);

        textureWidth = innerWidth;
        textureHeight = innerHeight;
      }

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferId);

      GL30.glClearColor(0f, 0f, 0f, 1f);
      GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);

      _graphActor.setWidth((float)innerWidth);
      _graphActor.setHeight((float)innerHeight);

      _graphStage.draw();

      GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

      ImGui.getWindowDrawList().addImage(
              textureId,
              ImGui.getCursorScreenPosX(),
              ImGui.getCursorScreenPosY(),
              ImGui.getWindowPosX() + ImGui.getWindowSizeX(),
              ImGui.getWindowPosY() + ImGui.getWindowSizeY()
      );

      ImGui.end();
    }

    ImGui.showMetricsWindow();

    ImGui.render();
    ImGuiHelper.getImGuiGl3().renderDrawData(ImGui.getDrawData());
  }
}
