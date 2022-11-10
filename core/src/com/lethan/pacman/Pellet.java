package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Pellet {

    private World world;
    private float x, y;
    private double arrayX, arrayY;
    private boolean isEaten;

    public Pellet(World world, int x, int y) {
        this.world = world;
        this.arrayX = x;
        this.arrayY = y;
        this.x = x*world.getWorldScale()+world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+world.getWorldScale()/2F;
        this.isEaten = false;
    }

    public void render(SpriteBatch batch) {
        if (!isEaten) {
        }
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        if (!isEaten) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x-world.getWorldScale() / 4F, y-world.getWorldScale() / 4F,
                    world.getWorldScale() / 2F, world.getWorldScale() / 2F);
            shapeRenderer.end();
        }
    }

    public void checkIfEaten() {
        if (!isEaten) {
            Player pacman = this.world.getPlayer();
            double pelletDistance = Math.sqrt(Math.pow((x - pacman.getX()), 2) + Math.pow((y - pacman.getY()), 2));
            if (pelletDistance < world.getWorldScale() / 4F) {
                this.isEaten = true;
            }
        }
    }

}
