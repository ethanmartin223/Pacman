package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fruit {
    private float x, y;
    private World world;
    private boolean eaten;
    private int value;
    private Sprite sprite;
    private static final int[] FRUIT_VALUES = new int[] {100,300,500,700,1000,2000,3000,5000};
    private float timeSpawned;
    private static final float decayTime = (float) (Math.random()+9);

    public Fruit(World world, float x, float y) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.eaten = false;
        this.value = FRUIT_VALUES[this.world.getLevelNumber()%8];
        this.sprite = world.getTextureAtlas().createSprite("fruit",
                (Math.min(world.getLevelNumber(), 8)));
        sprite.setSize(world.getWorldScale()*1.5F, world.getWorldScale()*1.5F);
        sprite.setPosition(x*world.getWorldScale()-world.getWorldScale()*.375F,
                y*world.getWorldScale()-world.getWorldScale()*.375F);
        sprite.flip(false, true);
    }

    public void checkIfEaten() {
        if (!this.eaten) {
            if (timeSpawned >= decayTime) {
                this.eaten = true;
                world.destroyFruit();
            } else timeSpawned += Gdx.graphics.getDeltaTime();
            Player pacman = this.world.getPlayer();
            if (Math.sqrt(Math.pow(((x*world.getWorldScale()+ world.getWorldScale()*.5F) - pacman.getX()), 2) +
                    Math.pow(((y*world.getWorldScale()+ world.getWorldScale()*.5F) - pacman.getY()), 2)) <=
                    (world.getWorldScale() / 4F)) {
                this.eaten = true;
                world.destroyFruit();
                pacman.addScore(this.value);
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
}
