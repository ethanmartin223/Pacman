package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.List;
import java.util.ArrayList;

public class World {

    private int[][] worldLayout;
    private List<Wall> wallList;
    private List<Pellet> pelletList;
    private List<PowerPellet> powerPelletList;
    private int worldScale;
    private Player player;

    private TextureAtlas textureAtlas;

    public World(TextureAtlas atlas, int worldSF) {
        this.worldScale = worldSF;
        wallList = new ArrayList<Wall>();
        pelletList = new ArrayList<Pellet>();
        powerPelletList = new ArrayList<PowerPellet>();

        textureAtlas = atlas;
        this.loadLevel(Levels.LEVEL_1);
        for (Wall w : wallList) w.determineSprite();
    }

    public int getWorldScale() {
        return worldScale;
    }

    public void loadLevel(Levels level) {
        pelletList.clear();
        wallList.clear();
        worldLayout = Levels.getLevel(level);
        for (int y=0; y < worldLayout.length; y++) {
            for (int x = 0; x < worldLayout[y].length; x++) {
                if (worldLayout[y][x] == 1) wallList.add(new Wall(this, x,y));
                if (worldLayout[y][x] == 2) {
                    pelletList.add(new Pellet(this, x,y));
                    worldLayout[y][x] = 0;
                }
                if (worldLayout[y][x] == 3) {
                    powerPelletList.add(new PowerPellet(this, x, y));
                    worldLayout[y][x] = 0;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Wall wall : wallList) wall.render(batch);
        for (Pellet pellet: pelletList) pellet.render(batch);
        for (PowerPellet powerPellet: powerPelletList) powerPellet.render(batch);
        this.player.render(batch);
        this.player.getScore();
    }

    public void update() {
        for (Pellet pellet: pelletList) pellet.checkIfEaten();
        for (PowerPellet powerPellet : powerPelletList) powerPellet.checkIfEaten();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        for (Pellet pellet: pelletList) pellet.debugRender(shapeRenderer);
        for (PowerPellet powerPellet: powerPelletList) powerPellet.debugRender(shapeRenderer);
        for (Wall w: wallList) w.debugRender(shapeRenderer);
        this.player.debugRender(shapeRenderer);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Pellet> getPellets() {
        return this.pelletList;
    }

    public int[][] getLayout() {
        return worldLayout;
    }

    public List<Wall> getWalls() {
        return wallList;
    }

    public TextureAtlas getTextureAtlas() {return textureAtlas;}
}

