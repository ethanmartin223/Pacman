package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Ghost {
    private World world;
    private int relX, relY;
    private float x,y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;

    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public Ghost(World world, float x, float y) {
        this.world = world;
        this.relX = (int)x;
        this.relY = (int)y;
        this.y = y;
        this.x = x;
        this.sprites = null;
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.currentSprite = name+"_idle";
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        sprites.get(currentSprite).draw(spriteBatch);
        spriteBatch.end();
    }

    private void moveToPosition(int x, int y) {

    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }
}
