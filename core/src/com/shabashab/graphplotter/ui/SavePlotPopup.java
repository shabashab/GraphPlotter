package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.actors.GraphActor;
import com.shabashab.graphplotter.ui.elements.ImGuiPopup;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;

import javax.swing.*;
import java.io.File;

public class SavePlotPopup extends ImGuiPopup {
  private static final float POPUP_WIDTH = 300;
  private static final float POPUP_HEIGHT = 300;

  private final ImString _filePathString;

  private final ImBoolean _transparentBackground;
  private final ImBoolean _showTitle;

  private final ImInt _imageWidth;
  private final ImInt _imageHeight;

  public SavePlotPopup(GuiElementsPool elementsPool) {
    super(elementsPool, "Save plot image");

    _filePathString = new ImString("", 500);

    _transparentBackground = new ImBoolean(false);
    _showTitle = new ImBoolean(true);

    _imageWidth = new ImInt(1000);
    _imageHeight = new ImInt(1000);
  }

  @Override
  protected void setup() {
    float windowWidth = ImGui.getContentRegionAvailX();
//    float windowHeight = ImGui.getContentRegionAvailY();

    ImGui.labelText("##filePath", "Save path:");

    ImGui.setNextItemWidth(windowWidth - 65f);
    ImGui.inputText("##filePath", _filePathString);
    ImGui.sameLine();

    if(ImGui.button("Open", 60f, 0f)) {
      JFileChooser fileChooser = new JFileChooser();

      if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        _filePathString.set(selectedFile.getAbsolutePath());
      }
    }

    ImGui.checkbox("Transparent background", _transparentBackground);
    ImGui.checkbox("Show title", _showTitle);

    ImGui.labelText("##imageWidth", "Image width");
    ImGui.inputInt("##imageWidth", _imageWidth);

    ImGui.labelText("##imageHeight", "Image height");
    ImGui.inputInt("##imageHeight", _imageHeight);

    if(ImGui.button("Save")) {
      FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, _imageWidth.get(), _imageHeight.get(), false);

      Viewport viewport = new ScreenViewport();

      Stage stage = new Stage();

      stage.setViewport(viewport);

      GraphActor graphActor = this.getElementsPool().getGraphWindow().getGraphActor().createCopy();

      if(_showTitle.get()) {
        graphActor.setDisplayGraphTitle(true);
        graphActor.setGraphTitle(this.getElementsPool().getGraphWindow().getGraphActor().getGraphTitle());
      }

      stage.addActor(graphActor);

      float prevGraphActorWidth = graphActor.getWidth();
      float prevGraphActorHeight = graphActor.getHeight();

      graphActor.setWidth(_imageWidth.get());
      graphActor.setHeight(_imageHeight.get());

      frameBuffer.begin();

      if(!_transparentBackground.get()) {
        ScreenUtils.clear(Color.BLACK);
      }

      stage.draw();

      Pixmap framebufferPixmap = Pixmap.createFromFrameBuffer(0, 0, _imageWidth.get(), _imageHeight.get());

      FileHandle fileHandle = new FileHandle(_filePathString.get());
      PixmapIO.writePNG(fileHandle, framebufferPixmap);

      frameBuffer.end();

      frameBuffer.dispose();
      stage.dispose();

      graphActor.setWidth(prevGraphActorWidth);
      graphActor.setHeight(prevGraphActorHeight);
    }

    ImGui.sameLine();

    if(ImGui.button("Close")) {
      close();
    }
  }

  @Override
  protected void beforeBegin() {
    ImGui.setNextWindowSize(POPUP_WIDTH, 0f, ImGuiCond.FirstUseEver);
  }
}
