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
    private PathfindingEngine pathfinder;
    private float animationTime;
    private int levelNumber;
    private Fruit fruit;

    private TextureAtlas textureAtlas;

    public World(TextureAtlas atlas, int worldSF) {
        this.animationTime = 0;
        this.worldScale = worldSF;
        wallList = new ArrayList<Wall>();
        pelletList = new ArrayList<Pellet>();
        powerPelletList = new ArrayList<PowerPellet>();
        ghostList = new ArrayList<Ghost>();

        textureAtlas = atlas;
        levelNumber = 1;
        this.loadLevel(Levels.LEVEL_1);
        pathfinder = new PathfindingEngine(worldLayout);
        for (Wall w : wallList) w.determineSprite();
    }

    public int getWorldScale() {
        return worldScale;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void loadLevel(Levels level) {
        pelletList.clear();
        wallList.clear();
        powerPelletList.clear();
        ghostList.clear();
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
                if (worldLayout[y][x] == 4) {
                    fruit = (new Fruit(this, x, y, this.getLevelNumber()));
                    worldLayout[y][x] = 0;
                }
                if (worldLayout[y][x] == 9) {
                    ghostList.add(new Blinky(this, x, y));
                }
                if (worldLayout[y][x] == 8) {
                    ghostList.add(new Pinky(this, x, y));
                    worldLayout[y][x] = 0;
                }
                if (worldLayout[y][x] == 7) {
                    ghostList.add(new Clyde(this, x, y));
                    worldLayout[y][x] = 0;
                }
            }
        }
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
        player.render(batch);
        for (Ghost ghost : ghostList) {
            ghost.update();
            ghost.render(batch);
        }
        if (fruit != null) {
            fruit.render(batch);
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

    public PathfindingEngine getPathfinder() {
        return pathfinder;
    }

    public List<PathfindingEngine.Point> getPath(int sX, int sY, int eX, int eY) {
        return pathfinder.getPath(sX,sY,eX,eY);
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}

