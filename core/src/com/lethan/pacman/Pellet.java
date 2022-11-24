package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Pellet {

    private World world;
    private float x, y;
    private double arrayX, arrayY;
    private boolean isEaten;
    private Sprite sprite;

    public Pellet(World world, int x, int y) {
        this.world = world;
        this.arrayX = x;
        this.arrayY = y;
        this.x = x*world.getWorldScale()+world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+world.getWorldScale()/2F;
        this.isEaten = false;
        this.sprite = world.getTextureAtlas().createSprite("pellet");
        sprite.setPosition(this.x-world.getWorldScale()/2F,this.y-world.getWorldScale()/2F);
        sprite.setSize(world.getWorldScale(), world.getWorldScale());
    }

    public void render(SpriteBatch batch) {
        if (!isEaten) {
            batch.begin();
            sprite.draw(batch);
            batch.end();
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
                pacman.addScore(10);
                pacman.addPelletsEaten(1);
            }
        }
    }

}
