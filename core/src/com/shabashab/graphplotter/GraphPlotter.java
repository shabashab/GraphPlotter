package com.shabashab.graphplotter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.shabashab.graphplotter.ui.ImGuiHelper;
import com.shabashab.graphplotter.ui.UIActor;

public class GraphPlotter extends ApplicationAdapter {
	private Stage _uiStage;
	private UIActor _uiActor;

	public GraphPlotter() {
	}

	@Override
	public void create() {
		ImGuiHelper.initializeImGui();

		_uiStage = new Stage();
		_uiActor = new UIActor();

		_uiStage.addActor(_uiActor);

		Gdx.input.setInputProcessor(_uiStage);
		_uiStage.setScrollFocus(_uiActor);
		_uiStage.setKeyboardFocus(_uiActor);
	}

	@Override
	public void resize(int width, int height) {
		_uiStage.getViewport().update(width, height, true);
		_uiActor.setBounds(0, 0, width, height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Color.CYAN);

		_uiStage.draw();
	}

	@Override
	public void dispose () {
		_uiStage.dispose();
	}
}
