package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class World {

    private int[][] worldLayout;
    private List<Wall> wallList;

    public World(int wallScale) {
        wallList = new ArrayList<Wall>();
        worldLayout = Levels.getLevel(Levels.LEVEL_1);

        for (int y=0; y < worldLayout.length; y++) {
            for (int x = 0; x < worldLayout[y].length; x++) {
                if (worldLayout[y][x] == 1) wallList.add(new Wall(x,y,wallScale,wallScale));
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Wall wall : wallList) {
            wall.render(shapeRenderer);
        }
        shapeRenderer.end();
    }
}

