package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Map;

public class Ghost {
    private World world;
    private float relX, relY;
    private float x,y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private float moveSpeed;
    private int[] nextLocation;
    private Rectangle bounds;

    private final Animation<TextureRegion> UP_ANIMATION;
    
    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    

    public Ghost(World world, float x, float y) {
        UP_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_up"));

        this.secondsBetweenMove =.01;
        this.world = world;
        this.relX = (int)x;
        this.relY = (int)y;
        this.y = y*world.getWorldScale();
        this.x = x*world.getWorldScale();
        this.sprites = null;
        this.moveSpeed = .1F;
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.currentSprite = name+"_idle";
        this.bounds = new Rectangle(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,
                (float)this.world.getWorldScale(),(float)this.world.getWorldScale());

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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0,1,1,1);
        shapeRenderer.rect(this.x,this.y,(float)this.world.getWorldScale(),(float)this.world.getWorldScale());
        shapeRenderer.end();
    }

    public void followPlayer() {
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            Player pacman = world.getPlayer();

            // "possible" bottleneck here.
            // change to if player x,y is not a wall, don't bother running alg first
            List<PathfindingEngine.Point> l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5) , (int)(pacman.getRelY()+.5));
            if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)+1 , (int)(pacman.getRelY()+.5)+1);
            if (l == null) l = world.getPath((int) relX, (int) relY, (int)(pacman.getRelX()+.5)-1 , (int)(pacman.getRelY()+.5)-1);
            // -----------------
            if (l != null) {
                if (l.get(0).x > relX) relX+=moveSpeed;
                else if (l.get(0).x < relX) relX-=moveSpeed;
                else if (l.get(0).y > relY) relY+=moveSpeed;
                else if (l.get(0).y < relY) relY-=moveSpeed;

                this.x = (float) (relX * this.world.getWorldScale());
                this.y = (float) (relY * this.world.getWorldScale());
                this.bounds.set(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,(float)this.world.getWorldScale(),(float)this.world.getWorldScale());
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
