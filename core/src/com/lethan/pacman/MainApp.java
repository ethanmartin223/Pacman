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
	private Ghost blinky, inky, pinky, clyde;
	private PathfindingEngine pathfinder;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		atlas = new TextureAtlas(Gdx.files.internal("assets/sprites.atlas"));

		world = new World(atlas, 20);
		player = new Player(world,13.5F,23F);
		clyde = new Clyde(world,1,29);
		blinky = new Blinky(world, 1, 29);
	}

	@Override
	public void render () {
		player.move();
		world.update();
		blinky.followPlayer();

		ScreenUtils.clear(0, 0, 0, 1);

		world.render(spriteBatch);
		world.debugRender(shapeRenderer);
		player.render(spriteBatch);
		blinky.render(spriteBatch);
		blinky.debugRender(shapeRenderer);

	}
	
	@Override
	public void dispose () {
		atlas.dispose();
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}
}
