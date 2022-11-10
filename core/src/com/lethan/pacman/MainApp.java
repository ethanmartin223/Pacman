package com.lethan.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainApp extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	SpriteBatch spriteBatch;
	OrthographicCamera camera;

	//Game Classes
	World world;
	Player player;
	TextureAtlas atlas;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		atlas = new TextureAtlas(Gdx.files.internal("assets/walls.pack"));
		TextureAtlas.AtlasRegion region = atlas.findRegion("0");

		world = new World(atlas, 20);
		player = new Player(world,13.5F,23F);

	}

	@Override
	public void render () {
		player.move();
		world.update();

		ScreenUtils.clear(0, 0, 0, 1);

		world.render(spriteBatch);
		//world.debugRender(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		atlas.dispose();
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}
}
