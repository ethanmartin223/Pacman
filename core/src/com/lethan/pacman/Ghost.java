package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Ghost {
    private World world;
    private float relX, relY;
    private float x, y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private float moveSpeed;
    private int[] nextLocation;
    private Rectangle bounds;
    private int[] direction;

    //anima
    private final Animation<TextureRegion> UP_ANIMATION;
    private final Animation<TextureRegion> DOWN_ANIMATION;
    private final Animation<TextureRegion> LEFT_ANIMATION;
    private final Animation<TextureRegion> RIGHT_ANIMATION;

    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private static final int[] UP = new int[]{0, -1};
    private static final int[] DOWN = new int[]{0, 1};
    private static final int[] RIGHT = new int[]{1, 0};
    private static final int[] LEFT = new int[]{-1, 0};
    private static final int[] IDLE = new int[]{0, 0};


    public Ghost(World world, float x, float y) {
        UP_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_up"));
        LEFT_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_left"));
        DOWN_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_down"));
        RIGHT_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("blinky_right"));


        this.secondsBetweenMove = .01;
        this.world = world;
        this.relX = x;
        this.relY = y;
        this.y = y * world.getWorldScale();
        this.x = x * world.getWorldScale();
        this.sprites = null;
        this.moveSpeed = .1F;
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.currentSprite = name + "_idle";
        this.bounds = new Rectangle(this.x - this.world.getWorldScale() / 2F, this.y - this.world.getWorldScale() / 2F,
                (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        this.direction = Ghost.IDLE;

    }

    public void render(SpriteBatch spriteBatch) {
        Animation<TextureRegion> currentAnimation;
        if (direction == Ghost.UP) currentAnimation = UP_ANIMATION;
        else if (direction == Ghost.DOWN) currentAnimation = DOWN_ANIMATION;
        else if (direction == Ghost.LEFT) currentAnimation = RIGHT_ANIMATION;
        else if (direction == Ghost.RIGHT) currentAnimation = LEFT_ANIMATION;
        else if (direction == Ghost.IDLE) currentAnimation = DOWN_ANIMATION;
        else currentAnimation = null;

        spriteBatch.begin();
        spriteBatch.draw(currentAnimation.getKeyFrame(world.getAnimationTime(), true),
                x + world.getWorldScale() * 1.25F, y + world.getWorldScale() * 1.25F, -world.getWorldScale() * 1.5F,
                -world.getWorldScale() * 1.5F);
        spriteBatch.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1F, 0F, 1F, .05F);
        Player pacman = world.getPlayer();
        List<PathfindingEngine.Point> l = world.getPath((int) (relX + .5), (int) (relY + .5), (int) (pacman.getRelX() + .5), (int) (pacman.getRelY() + .5));
        if (l == null)
            l = world.getPath((int) relX, (int) relY, (int) (pacman.getRelX() + .5) + 1, (int) (pacman.getRelY() + .5) + 1);
        if (l == null)
            l = world.getPath((int) relX, (int) relY, (int) (pacman.getRelX() + .5) - 1, (int) (pacman.getRelY() + .5) - 1);
        if (l != null) {
            for (PathfindingEngine.Point j : l) {
                shapeRenderer.rect(j.x * world.getWorldScale(), j.y * world.getWorldScale(), world.getWorldScale(), world.getWorldScale());
            }
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(this.x + this.direction[0] * moveSpeed, this.y + this.direction[1] * moveSpeed, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        shapeRenderer.end();
    }

    public boolean willNotCollide() {
        this.bounds.set(this.x + this.direction[0] * moveSpeed, this.y + this.direction[1] * moveSpeed, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        for (Wall w : world.getWalls()) {
            if (Intersector.overlaps(w.getBounds(), this.bounds)) {
                return false;
            }
        }
        return true;
    }

    public void followPlayer() {
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            Player pacman = world.getPlayer();

            // "possible" bottleneck here.
            // change to if player x,y is not a wall, don't bother running alg first
            List<PathfindingEngine.Point> l = world.getPath((int) (relX), (int) (relY), (int) (pacman.getRelX() + .5), (int) (pacman.getRelY() + .5));
            if (l == null)
                l = world.getPath((int) (relX), (int) (relY), (int) (pacman.getRelX() + .5) + 1, (int) (pacman.getRelY() + .5) + 1);
            if (l == null)
                l = world.getPath((int) (relX), (int) (relY), (int) (pacman.getRelX() + .5) - 1, (int) (pacman.getRelY() + .5) - 1);

            // -----------------
            if (l != null) {
                if (willNotCollide() && (!(direction == Ghost.IDLE))) {
                    if (direction == Ghost.UP && l.get(0).y == Math.round(relY + .5)) {
                        relY = l.get(1).y;
                        direction = Ghost.IDLE;
                    } else if (direction == Ghost.DOWN && l.get(0).y == Math.round(relY - .5)) {
                        relY = l.get(1).y;
                        direction = Ghost.IDLE;
                    } else if (direction == Ghost.RIGHT && l.get(0).x == Math.round(relX - .5)) {
                        relX = l.get(1).x;
                        direction = Ghost.IDLE;
                    } else if (direction == Ghost.LEFT && l.get(0).x == Math.round(relX + .5)) {
                        relX = l.get(1).x;
                        direction = Ghost.IDLE;
                    }


                    relX += moveSpeed * direction[0];
                    relY += moveSpeed * direction[1];
                } else {
                    relX = Math.round(relX);
                    relY = Math.round(relY);
                    if (l.get(1).x < relX) direction = Ghost.LEFT;
                    else if (l.get(1).x > relX) direction = Ghost.RIGHT;
                    else if (l.get(1).y > relY) direction = Ghost.DOWN;
                    else if (l.get(1).y < relY) direction = Ghost.UP;
                }
                this.x = ((relX) * this.world.getWorldScale());
                this.y = ((relY) * this.world.getWorldScale());
                this.bounds.set(this.x, this.y, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
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
