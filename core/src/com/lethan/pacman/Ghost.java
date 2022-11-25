package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Ghost {
    protected World world;
    protected float relX, relY;
    protected float x, y;
    protected Map<String, Sprite> sprites;
    protected String currentSprite;
    protected final String name;
    protected float lastMoveDeltaTime;
    protected double secondsBetweenMove;
    protected float moveSpeed, normalMoveSpeed, slowedSpeed;
    protected int[] lastDirection;
    protected Rectangle bounds;
    protected int[] direction;
    protected GhostAttackMode mode;
    protected float targetX, targetY;

    //anima
    protected final Animation<TextureRegion> UP_ANIMATION;
    protected final Animation<TextureRegion> DOWN_ANIMATION;
    protected final Animation<TextureRegion> LEFT_ANIMATION;
    protected final Animation<TextureRegion> RIGHT_ANIMATION;
    protected final Animation<TextureRegion> FRIGHTENED_ANIMATION;

    //const
    protected static final int[] UP = new int[]{0, -1};
    protected static final int[] DOWN = new int[]{0, 1};
    protected static final int[] RIGHT = new int[]{1, 0};
    protected static final int[] LEFT = new int[]{-1, 0};
    protected static final int[] IDLE = new int[]{0, 0};
    protected static final int[][] VALID_MOVES = new int[][]{UP, LEFT, DOWN, RIGHT};

    protected double timeSinceLastScatter;
    protected double timeInScatterMode;
    protected double nextScatterTime;
    protected double totalScatterTimeDuration;
    protected int scatterNumber;

    protected final static double[] LEVEL_TYPE_A_SCATTER_TIMES = new double[] {7,7,5,5};
    protected final static double[] LEVEL_TYPE_B_SCATTER_TIMES = new double[] {7,7,5,1.0/60};
    protected final static double[] LEVEL_TYPE_C_SCATTER_TIMES = new double[] {5,5,5,1.0/60};
    protected final static double[] LEVEL_TYPE_A_CHASE_TIMES = new double[] {20,20,20,-1};
    protected final static double[] LEVEL_TYPE_B_CHASE_TIMES = new double[] {20,20,1033,-1};
    protected final static double[] LEVEL_TYPE_C_CHASE_TIMES = new double[] {20,20,1037,-1};

    protected final static int[] FRIGHTENED_TIME_BY_LEVEL = new int[] {6,5,4,3,5,5,2,2,1,5,2,2,1,3,1,1,0,1,0};

    protected double[] currentScatterTimes;
    protected double[] currentChaseTimes;
    protected float timeInFrightenedMode;
    protected float totalFrightenedModeTime;

    public Ghost(World world, float x, float y) {
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.secondsBetweenMove = .01;
        this.world = world;
        this.relX = x;
        this.relY = y;
        this.y = y * world.getWorldScale();
        this.x = x * world.getWorldScale();
        this.sprites = null;

        this.currentSprite = name + "_idle";
        this.bounds = new Rectangle(this.x - this.world.getWorldScale() / 2F, this.y - this.world.getWorldScale() / 2F,
                (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        this.direction = Ghost.IDLE;
        this.lastDirection = this.direction;

        this.targetX = 0;
        this.targetY = 0;

        if (world.getLevelNumber() == 1) {
            this.currentChaseTimes = LEVEL_TYPE_A_CHASE_TIMES;
            this.currentScatterTimes = LEVEL_TYPE_A_SCATTER_TIMES;
        } else if (world.getLevelNumber() > 1 && world.getLevelNumber() < 5) {
            this.currentChaseTimes = LEVEL_TYPE_B_CHASE_TIMES;
            this.currentScatterTimes = LEVEL_TYPE_B_SCATTER_TIMES;
        } else {
            this.currentChaseTimes = LEVEL_TYPE_C_CHASE_TIMES;
            this.currentScatterTimes = LEVEL_TYPE_C_SCATTER_TIMES;
        }

        this.moveSpeed = .1F;
        this.normalMoveSpeed = moveSpeed;
        this.slowedSpeed = moveSpeed*.5F;

        //scatter time settings
        this.scatterNumber = 0;
        this.timeSinceLastScatter = 0;
        this.timeInScatterMode = 0;
        this.totalScatterTimeDuration = currentScatterTimes[scatterNumber];
        this.nextScatterTime = currentChaseTimes[scatterNumber];

        //frightened time
        this.timeInFrightenedMode = 0;
        this.totalFrightenedModeTime = world.getLevelNumber()<19?FRIGHTENED_TIME_BY_LEVEL[world.getLevelNumber()-1]:0;

        //anima
        UP_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions(name+"_up"));
        LEFT_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions(name+"_left"));
        DOWN_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions(name+"_down"));
        RIGHT_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions(name+"_right"));
        FRIGHTENED_ANIMATION = new Animation<TextureRegion>(0.1f, world.getTextureAtlas().findRegions("ghost_mortal"));
    }

    public void determineMode() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            if (timeSinceLastScatter >= nextScatterTime && nextScatterTime > 0) {
                timeSinceLastScatter = 0;
                this.nextScatterTime = currentChaseTimes[scatterNumber];
                setMode(GhostAttackMode.SCATTER_MODE);
            } else timeSinceLastScatter += Gdx.graphics.getDeltaTime();
        } else if (mode == GhostAttackMode.SCATTER_MODE) {
            if (timeInScatterMode >= totalScatterTimeDuration) {
                timeInScatterMode = 0;
                scatterNumber += scatterNumber>=3?0:1;
                this.totalScatterTimeDuration = currentScatterTimes[scatterNumber];
                setMode(GhostAttackMode.SICKO_MODE);
            } else timeInScatterMode += Gdx.graphics.getDeltaTime();
        } else if (mode == GhostAttackMode.FRIGHTENED) {
            if (timeInFrightenedMode >= totalFrightenedModeTime) {
                timeInFrightenedMode = 0;
                setMode(GhostAttackMode.SICKO_MODE);
            } else timeInFrightenedMode += Gdx.graphics.getDeltaTime();
        }
    }

    public void setMode(GhostAttackMode m) {
        if (mode != GhostAttackMode.FRIGHTENED) {
            mode = m;
            if (direction == Ghost.UP) direction = DOWN;
            else if (direction == Ghost.DOWN) direction = UP;
            else if (direction == Ghost.LEFT) direction = RIGHT;
            else if (direction == Ghost.RIGHT) direction = LEFT;
        } else {
            mode = m;
            moveSpeed *= 2;
        }
    }

    public void render(SpriteBatch spriteBatch) {
        Animation<TextureRegion> currentAnimation;
        if (mode != GhostAttackMode.FRIGHTENED) {
            if (direction == Ghost.UP) currentAnimation = UP_ANIMATION;
            else if (direction == Ghost.DOWN) currentAnimation = DOWN_ANIMATION;
            else if (direction == Ghost.LEFT) currentAnimation = RIGHT_ANIMATION;
            else if (direction == Ghost.RIGHT) currentAnimation = LEFT_ANIMATION;
            else if (direction == Ghost.IDLE) currentAnimation = DOWN_ANIMATION;
            else currentAnimation = null;
        } else {
            currentAnimation = FRIGHTENED_ANIMATION;
        }
        spriteBatch.begin();
        spriteBatch.draw(currentAnimation.getKeyFrame(world.getAnimationTime(), true),
                x + world.getWorldScale() * 1.25F, y + world.getWorldScale() * 1.25F, -world.getWorldScale() * 1.5F,
                -world.getWorldScale() * 1.5F);
        spriteBatch.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(.8f, .5f, 1, 1);
        shapeRenderer.rect(this.x + this.direction[0] * moveSpeed, this.y + this.direction[1] * moveSpeed, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        shapeRenderer.circle(targetX*world.getWorldScale()+world.getWorldScale() *.5F,targetY*world.getWorldScale()+world.getWorldScale() *.5F, world.getWorldScale()/2F);
        shapeRenderer.end();
    }

    public boolean willNotCollide(float x, float y, int[] direction) {
        this.bounds.set(x + direction[0] * moveSpeed, y + direction[1] * moveSpeed, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        for (Wall w : world.getWalls()) {
            if (Intersector.overlaps(w.getBounds(), this.bounds)) {
                return false;
            }
        }
        return true;
    }

    public boolean willCauseTurnAround(int[] dir) {
        if ((dir == UP && direction == DOWN) || (dir == DOWN && direction == UP)) return true;
        else return (dir == LEFT && direction == RIGHT) || (dir == RIGHT && direction == LEFT);
    }

    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void moveTo(int dx, int dy) {
        determineMode();
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            int[] bestDirection = Ghost.IDLE;
            double bestScore = Integer.MAX_VALUE;
            for (int[] dir : VALID_MOVES) {
                if (willNotCollide(this.x, this.y, dir)) {
                    if ((Math.abs(dx-(relX+dir[0]))+Math.abs(dy-(relY+dir[1]))) < bestScore && (!willCauseTurnAround(dir))) {
                        bestDirection = dir;
                        bestScore = (Math.abs(dx-(relX+dir[0]))+Math.abs(dy-(relY+dir[1])));
                    }
                }
            }
            this.direction = bestDirection;

            relX = (float) round(relX+direction[0]*moveSpeed,2);
            relY = (float) round(relY+direction[1]*moveSpeed,2);

            this.x = ((relX) * this.world.getWorldScale());
            this.y = ((relY) * this.world.getWorldScale());
            this.bounds.set(this.x, this.y, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public void moveRandom() {
        determineMode();
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastMoveDeltaTime = 0;
            int[] bestDirection = Ghost.IDLE;
            double bestScore = Integer.MAX_VALUE;
            for (int[] dir : VALID_MOVES) {
                if (willNotCollide(this.x, this.y, dir)) {
                    if (bestScore > Math.random() && !willCauseTurnAround(dir)) {
                        bestDirection = dir;
                        bestScore = Math.random();
                    }
                }
            }
            this.direction = bestDirection;
            relX = (float) round(relX+direction[0]*moveSpeed,2);
            relY = (float) round(relY+direction[1]*moveSpeed,2);
            this.x = ((relX) * this.world.getWorldScale());
            this.y = ((relY) * this.world.getWorldScale());
            this.bounds.set(this.x, this.y, (float) this.world.getWorldScale(), (float) this.world.getWorldScale());
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getRelX() {
        return relX;
    }

    public float getRelY() {
        return relY;
    }

    public void setMoveSpeed(float i) {
        moveSpeed = i;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void update() {
    }

    public float getCurrentSlowedSpeed() {
        return slowedSpeed;
    }
}
