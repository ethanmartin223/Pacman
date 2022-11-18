package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fruit {
    private boolean hasAppeared = false;
    private World world;

    public Fruit(World world, double x, double y, int level){
        this.hasAppeared = false;
        this.world = world;
    }

    public boolean hasAppeared() {
        return hasAppeared;
    }

    public void spawn() {
        if (Math.random() == 0) {
            hasAppeared = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (hasAppeared) {
            batch.begin();
            //batch.draw();
            batch.end();
        }

    }

}
