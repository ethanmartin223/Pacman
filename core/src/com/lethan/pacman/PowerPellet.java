package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PowerPellet{
    World world;
    float x, y;
    double arrayY,arrayX;

    public PowerPellet(World world, int x, int y) {
        this.world = world;
        this.arrayX = x;
        this.arrayY = y;
        this.x = x*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+world.getWorldScale()/2F;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(x,y, world.getWorldScale()/4F);
        shapeRenderer.end();
    }
}
