package com.shabashab.graphplotter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shabashab.graphplotter.actors.GraphActor;

public class GraphPlotter extends ApplicationAdapter {
	private Stage _graphStage;
	private GraphActor _graphActor;

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

	@Override
	public void create () {
		final float GAME_WIDTH = Gdx.graphics.getWidth();
		final float GAME_HEIGHT = Gdx.graphics.getHeight();

		Camera camera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
		Viewport viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);


		_graphActor = new GraphActor();

		Vector2[] points = calculatePoints(-10f, 10f, 200);
		_graphActor.setPoints(points);

		_graphActor.setPosition(0f, 0f);
		_graphActor.setWidth(GAME_WIDTH);
		_graphActor.setHeight(GAME_HEIGHT);

		_graphStage = new Stage(viewport);
		Gdx.input.setInputProcessor(_graphStage);
		_graphStage.setScrollFocus(_graphActor);

		_graphStage.addActor(_graphActor);
	}

	@Override
	public void resize(int width, int height) {
		ScreenUtils.clear(Color.BLACK);

		_graphStage.getViewport().update(width, height);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);

		Vector2 graphActorOffset = _graphActor.getOffset();

		_graphActor.setOffset(graphActorOffset);

		_graphStage.draw();
	}

	@Override
	public void dispose () {
		_graphStage.dispose();
		_graphActor.dispose();
	}
}
