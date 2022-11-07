package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PowerPellet{
    World world;
    int x, y;

    public PowerPellet(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(this.x*world.getWorldScale()+this.world.getWorldScale()/2F,
                this.y*world.getWorldScale()+this.world.getWorldScale()/2F, world.getWorldScale()/4F);
        shapeRenderer.end();
    }
}
