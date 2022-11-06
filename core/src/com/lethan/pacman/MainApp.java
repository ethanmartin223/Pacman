package com.lethan.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainApp extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;

	//Game Classes
	World world;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		world = new World();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
