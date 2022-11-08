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
	OrthographicCamera camera;

	//Game Classes
	World world;
	Player player;
	TextureAtlas atlas;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);

		//atlas = new TextureAtlas(Gdx.files.internal());
		//TextureAtlas.AtlasRegion region = atlas.findRegion();

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);


		world = new World(20);
		player = new Player(world,13.5F,23F);

	}

	@Override
	public void render () {
		player.move();
		world.update();

		ScreenUtils.clear(0, 0, 0, 1);

		//world.render(shapeRenderer);
		world.debugRender(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
