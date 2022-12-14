package com.lethan.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainApp extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private TextureAtlas atlas;

	//Game Classes

	private World world;
	private Player player;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		atlas = new TextureAtlas(Gdx.files.internal("assets/packed_sprites/sprites.pack"));

		world = new World(atlas, 20);
		player = new Player(world,13.5F,23F);
		world.spawnBlinky(26F, 1F);
		world.spawnPinky(1F, 1F);
		world.spawnClyde(1F, 29F);
		world.spawnInky(26F, 29F);

	}

	@Override
	public void render () {
		player.move();

		ScreenUtils.clear(0, 0, 0, 1);

		world.render(spriteBatch);
		//world.debugRender(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		atlas.dispose();
		shapeRenderer.dispose();
		spriteBatch.dispose();
		player.dispose();
	}
}
