package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class Ghost {
    private World world;
    private double relX, relY;
    private float x,y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;

    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private float lastMoveDeltaTime;
    private float secondsBetweenMove;
    private float moveSpeed;

    public Ghost(World world, float x, float y) {
        this.secondsBetweenMove = 1;
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
        sprites.get(currentSprite).draw(spriteBatch);
        spriteBatch.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(.7F,.5F,.8F,1);
        List<PathfindingEngine.Point> l = world.getPath((int) relX, (int) relY,
                (int) world.getPlayer().getRelX(), (int) world.getPlayer().getRelY());
        if (l!=null) {
            for (PathfindingEngine.Point j : l) {
                shapeRenderer.rect(j.x * world.getWorldScale(), j.y * world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
            }
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,0,0,1);
        shapeRenderer.rect(this.x, this.y, world.getWorldScale(), world.getWorldScale());
        shapeRenderer.end();
    }

    public void followPlayer() {
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            Player pacman = world.getPlayer();
            List<PathfindingEngine.Point> l = world.getPath((int) relX, (int) relY, (int)pacman.getRelX(), (int) pacman.getRelY());
            System.out.println(relX+" "+relY);
            if (l != null) {
                System.out.println(Arrays.deepToString(l.toArray()));
                double xDiff = relX>l.get(0).x?relX-(l.get(0).x):(l.get(0).x)-relX;
                double yDiff = relY>l.get(0).y?relY-(l.get(0).y):(l.get(0).y)-relY;

                this.x = l.get(0).x* this.world.getWorldScale();
                this.relX = l.get(0).x;
                this.y = l.get(0).y* this.world.getWorldScale();
                this.relY = l.get(0).y;
            } else {
                System.out.println("l is null");

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
