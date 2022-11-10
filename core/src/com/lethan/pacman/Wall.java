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

        int[][] s = new int[3][3];
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if ((this.y + y)>0 && (this.x + x)>0 && (this.y + y)<l.length && (this.x + x)<l[0].length)
                    s[y + 1][x + 1] = l[this.y + y][this.x + x];
                else s[y + 1][x + 1] = 1;
            }
        }

        for (WallType w : WallType.values()) {
            if (Arrays.deepEquals(s, w.getLayout())) {
                System.out.println(w.getTextureName());
                return world.getTextureAtlas().createSprite(w.getTextureName());
            }
        }
        System.out.println(world.getTextureAtlas().createSprite("0"));
        return world.getTextureAtlas().createSprite("0");
    }


    public void render(SpriteBatch spriteBatch) {
        if (sprite != null) {
            sprite.setPosition((float) x * world.getWorldScale(), (float) y * world.getWorldScale());
            spriteBatch.begin();
            sprite.draw(spriteBatch);
            spriteBatch.end();
        }
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
