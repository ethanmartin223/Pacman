package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PowerPellet{
    World world;
    float x, y;
    double arrayY,arrayX;
    boolean isEaten;

    public PowerPellet(World world, int x, int y) {
        this.world = world;
        this.arrayX = x;
        this.arrayY = y;
        this.isEaten = false;
        this.x = x*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+world.getWorldScale()/2F;
    }

    public void render(SpriteBatch batch) {
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        if (!isEaten) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(x-world.getWorldScale() *.40F, y-world.getWorldScale() *.40F,
                    world.getWorldScale()*.75F, world.getWorldScale() *.75F);
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
