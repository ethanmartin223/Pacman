package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wall {

    int x, y, width, height;
    World world;
    Rectangle bounds;
    Sprite sprite;

    public Wall(World world, int x, int y) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.bounds = new Rectangle((float) x * world.getWorldScale(), (float) y * world.getWorldScale(), (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        this.sprite = determineSprite();
    }

    private Sprite determineSprite() {
        int[][] l = world.getLayout();
        TextureAtlas atlas = world.getTextureAtlas();

        int[][] s = new int[2][2];
        for (int y=-1; y<=1; y++) {
            for (int x = -1; x <= 1; x++) {
                s[y+1][x+1] = l[this.y + y][this.x + x];
            }
        }

        if (s.equals())

    }

    public void render(SpriteBatch spriteBatch) {
        sprite.setPosition((float)x*world.getWorldScale(),(float)y*world.getWorldScale());
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
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
