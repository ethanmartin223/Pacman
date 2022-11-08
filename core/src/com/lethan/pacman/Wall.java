package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Wall {

    int x, y, width, height;
    World world;
    Rectangle bounds;

    public Wall(World world, int x, int y) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.bounds = new Rectangle((float)x*world.getWorldScale(),(float)y*world.getWorldScale(),(float)this.world.getWorldScale(),(float)this.world.getWorldScale());
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(x*world.getWorldScale(), y*world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
        shapeRenderer.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(x * world.getWorldScale(), y * world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
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

    public Rectangle getBounds() {
        return bounds;
    }
}
