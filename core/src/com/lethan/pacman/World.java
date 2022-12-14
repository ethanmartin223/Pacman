package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
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
    private List<Ghost> ghostList;
    private int worldScale;
    private Player player;
    private float animationTime;
    private int levelNumber;
    private Ghost clyde, inky, pinky, blinky;
    private Fruit fruit;
    private int fruitsSpawned;

    private TextureAtlas textureAtlas;

    public World(TextureAtlas atlas, int worldSF) {
        this.animationTime = 0;
        this.worldScale = worldSF;
        wallList = new ArrayList<Wall>();
        pelletList = new ArrayList<Pellet>();
        powerPelletList = new ArrayList<PowerPellet>();
        ghostList = new ArrayList<Ghost>();

        textureAtlas = atlas;
        fruit = null;
        this.loadLevel(1);
        for (Wall w : wallList) w.determineSprite();
    }

    public int getWorldScale() {
        return worldScale;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void loadLevel(int level) {
        pelletList.clear();
        wallList.clear();
        powerPelletList.clear();
        ghostList.clear();
        levelNumber = level;
        fruitsSpawned = 0;
        worldLayout = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 9, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
                {1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1,},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1,},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1,},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1,},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,}
        };
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

    public void spawnBlinky(float x, float y) {
        blinky = new Blinky(this, x, y);
        ghostList.add(blinky);
    }
    public void spawnPinky(float x, float y) {
        pinky = new Pinky(this, x, y);
        ghostList.add(pinky);
    }
    public void spawnClyde(float x, float y) {
        clyde = new Clyde(this, x, y);
        ghostList.add(clyde);
    }
    public void spawnInky(float x, float y) {
        inky = new Inky(this, x, y);
        ghostList.add(inky);
    }
    public void spawnFruit() {
        fruit = new Fruit(this, 13.5F, 23F);
    }

    public Ghost getBlinky() {
        return blinky;
    }

    public Ghost getClyde() {
        return clyde;
    }

    public Ghost getInky() {
        return inky;
    }

    public Ghost getPinky() {
        return pinky;
    }

    public void render(SpriteBatch batch) {
        for (Wall wall : wallList) {
            wall.render(batch);
        }
        for (Pellet pellet: pelletList) {
            pellet.checkIfEaten();
            pellet.render(batch);
        }
        for (PowerPellet powerPellet: powerPelletList) {
            powerPellet.checkIfEaten();
            powerPellet.render(batch);
        }
        if (fruit != null) {
            fruit.render(batch);
            fruit.checkIfEaten();
        }
        player.render(batch);
        for (Ghost ghost : ghostList) {
            ghost.update();
            ghost.render(batch);
        }
        this.animationTime += Gdx.graphics.getDeltaTime();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        for (Pellet pellet: pelletList) pellet.debugRender(shapeRenderer);
        for (PowerPellet powerPellet: powerPelletList) powerPellet.debugRender(shapeRenderer);
        for (Wall w: wallList) w.debugRender(shapeRenderer);
        for (Ghost ghost : ghostList) ghost.debugRender(shapeRenderer);
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

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Ghost> getGhostList() {
        return ghostList;
    }

    public void destroyFruit() {
        fruit = null;
    }

    public int getFruitsSpawned() {
        return fruitsSpawned;
    }

    public void setFruitsSpawned(int i) {
        this.fruitsSpawned = i;
    }
}

