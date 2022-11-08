package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Wall {

    int x, y, width, height;
    World world;

    public Wall(World world, int x, int y) {
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(x*world.getWorldScale(), y*world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
        shapeRenderer.end();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean containsPoint(float x, float y) {
        return (this.x<x && this.y<y && this.y+1>y && this.x+1>x);
    }

}
