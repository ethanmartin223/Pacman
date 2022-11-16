package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class Ghost {
    private World world;
    private double relX, relY;
    private float x,y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private float moveSpeed;
    private int[] nextLocation;

    private final Animation<TextureRegion> UP_ANIMATION;
    
    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    

    public Ghost(World world, float x, float y) {
        UP_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_up"));

        this.secondsBetweenMove =.4;
        this.world = world;
        this.relX = (int)x;
        this.relY = (int)y;
        this.y = y*world.getWorldScale();
        this.x = x*world.getWorldScale();
        this.sprites = null;
        this.moveSpeed = 1.5F;
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.currentSprite = name+"_idle";
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(UP_ANIMATION.getKeyFrame(world.getAnimationTime(),true),
                x+world.getWorldScale()*1.25F, y+world.getWorldScale()*1.25F, -world.getWorldScale()*1.5F,
                -world.getWorldScale()*1.5F);
        spriteBatch.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(.7F,.5F,.8F,1);
        Player pacman = world.getPlayer();
        List<PathfindingEngine.Point> l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5) , (int)(pacman.getRelY()+.5));
        if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)+1 , (int)(pacman.getRelY()+.5)+1);
        if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)-1 , (int)(pacman.getRelY()+.5)-1);
        if (l!=null) {
            for (PathfindingEngine.Point j : l) {
                shapeRenderer.rect(j.x * world.getWorldScale(), j.y * world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
            }
        }
        shapeRenderer.end();*/
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,0,0,1);
        shapeRenderer.rect(this.x, this.y, world.getWorldScale(), world.getWorldScale());
        shapeRenderer.end();
    }

    public void followPlayer() {
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            Player pacman = world.getPlayer();

            // "possible" bottleneck here.
            List<PathfindingEngine.Point> l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5) , (int)(pacman.getRelY()+.5));
            if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)+1 , (int)(pacman.getRelY()+.5)+1);
            if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)-1 , (int)(pacman.getRelY()+.5)-1);
            // -----------------

            if (l != null) {
                this.x = l.get(0).x* this.world.getWorldScale();
                this.relX = l.get(0).x;
                this.y = l.get(0).y* this.world.getWorldScale();
                this.relY = l.get(0).y;
            }
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }
}
