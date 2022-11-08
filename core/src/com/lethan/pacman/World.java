package com.lethan.pacman;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;
import java.util.ArrayList;

public class World {

    private int[][] worldLayout;
    private List<Wall> wallList;
    private List<Pellet> pelletList;
    private List<PowerPellet> powerPelletList;
    private int worldScale;
    private Player player;

    public World(int worldSF) {
        this.worldScale = worldSF;
        wallList = new ArrayList<Wall>();
        pelletList = new ArrayList<Pellet>();
        powerPelletList = new ArrayList<PowerPellet>();
        this.loadLevel(Levels.LEVEL_1);
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

    public void render(ShapeRenderer shapeRenderer) {
        for (Wall wall : wallList){
            wall.render(shapeRenderer);
        }
        for (Pellet pellet: pelletList) {
            pellet.render(shapeRenderer);
        }
        for (PowerPellet powerPellet: powerPelletList) {
            powerPellet.render(shapeRenderer);
        }
        this.player.render(shapeRenderer);
    }

    public void update() {
        for (Pellet pellet: pelletList) {
            pellet.checkIfEaten();
        }
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        for (Pellet pellet: pelletList) {
            pellet.debugRender(shapeRenderer);
        }
        for (Wall w: wallList) {
            w.debugRender(shapeRenderer);
        }
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
}

