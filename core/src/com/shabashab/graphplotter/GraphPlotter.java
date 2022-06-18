package com.shabashab.graphplotter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.ui.GUIScene;
import com.shabashab.graphplotter.ui.ImGuiHelper;
import com.shabashab.graphplotter.ui.ImGuiScene;

public class GraphPlotter extends ApplicationAdapter {
	private Stage _uiStage;
	private ImGuiScene _uiScene;

	public GraphPlotter() {
	}

	@Override
	public void create() {
		ImGuiHelper.initializeImGui();

		_uiStage = new Stage();
		_uiScene = new GUIScene();

		_uiStage.addActor(_uiScene);

		Gdx.input.setInputProcessor(_uiStage);
		_uiStage.setScrollFocus(_uiScene);
		_uiStage.setKeyboardFocus(_uiScene);
	}

	@Override
	public void resize(int width, int height) {
		_uiStage.getViewport().update(width, height, true);
		_uiScene.setBounds(0, 0, width, height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Color.CYAN);

		_uiStage.draw();

		Gdx.graphics.setTitle("FPS:" + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose () {
		_uiStage.dispose();
	}
}
