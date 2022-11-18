package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Player {
    private static final int[] UP = new int[] {0,-1};
    private static final int[] DOWN = new int[] {0,1};
    private static final int[] RIGHT = new int[] {1,0};
    private static final int[] LEFT = new int[] {-1,0};
    private static final int[] IDLE = new int[] {0,0};
    private static final int[][] SUROUNDINGS = new int[][] {{}};
    private static final float ANIMATION_CHANGE_DELAY = .05F;

    private float x, y;
    private int[] direction;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private World world;
    private static float MOVE_SPEED = 2.5F;
    private float relX, relY;
    private int[] lastDirection;
    private Rectangle bounds;
    private ArrayList<Sprite> sprites;
    private int animationCycle = 0;
    private float timeSinceLaseAnimationChange;
    private int animationDirection;
    private int score;
    private int lives;
    private int[] nextDirection;


    public Player(World world, float x, float y) {
        world.setPlayer(this);
        this.world = world;
        this.x = x*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.direction = Player.IDLE;
        this.secondsBetweenMove = .01;
        this.lastMoveDeltaTime = 0;
        this.relX = x;
        this.relY = y;
        this.lastDirection = Player.IDLE;
        this.timeSinceLaseAnimationChange = 0;
        this.bounds = new Rectangle(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,
                (float)this.world.getWorldScale(),(float)this.world.getWorldScale());
        this.score = 0;
        this.lives = 4;
        this.nextDirection = Player.RIGHT;

        this.animationDirection = 1;
        this.sprites = new ArrayList<Sprite>();
        for (int i=0; i<3; i++) {
            sprites.add(world.getTextureAtlas().createSprite("pacman_frame",i+1));
            sprites.get(i).setSize(world.getWorldScale(), world.getWorldScale());
            sprites.get(i).setOrigin(world.getWorldScale()/2F, world.getWorldScale()/2F);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move() {
        this.getInput();
        if (lastMoveDeltaTime >= secondsBetweenMove) {
            lastDirection = direction;
            direction = nextDirection;
            if (!willNotCollide()) direction = lastDirection;
            if (this.willNotCollide()) {
                lastMoveDeltaTime = 0;
                lastDirection = this.direction;
                this.x += this.direction[0] * MOVE_SPEED;
                this.y += this.direction[1] * MOVE_SPEED;
                this.relX += (this.direction[0] * MOVE_SPEED) / this.world.getWorldScale();
                this.relY += (this.direction[1] * MOVE_SPEED) / this.world.getWorldScale();
                if (this.x < -this.world.getWorldScale()) {
                    this.x = world.getLayout()[0].length*world.getWorldScale();
                    this.relX = (this.x) / this.world.getWorldScale();
                }
                if (this.x > this.world.getLayout()[0].length*this.world.getWorldScale()+this.world.getWorldScale()) {
                    this.x = -world.getWorldScale();
                    this.relX = (this.x) / this.world.getWorldScale();
                }
                this.bounds.set(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,(float)this.world.getWorldScale(),(float)this.world.getWorldScale());

            }
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public void getInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction = Player.UP;
            if (lastDirection == Player.DOWN){
                nextDirection = Player.UP;
                return;
            }
            if (!willNotCollide() && lastDirection != Player.DOWN) {
                direction = lastDirection;
                nextDirection = Player.UP;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction = Player.DOWN;
            if (lastDirection == Player.UP){
                nextDirection = Player.DOWN;
                return;
            }
            if (!willNotCollide() && lastDirection != Player.UP) {
                direction = lastDirection;
                nextDirection = Player.DOWN;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction = Player.LEFT;
            if (lastDirection == Player.RIGHT){
                nextDirection = Player.LEFT;
                return;
            }
            if (!willNotCollide() && lastDirection != Player.RIGHT) {
                direction = lastDirection;
                nextDirection = Player.LEFT;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction = Player.RIGHT;
            if (lastDirection == Player.LEFT){
                nextDirection = Player.RIGHT;
                return;
            }
            if (!willNotCollide()) {
                direction = lastDirection;
                nextDirection = Player.RIGHT;
            }
        }
    }

    public boolean willNotCollide() {
        this.bounds.set(this.x-this.world.getWorldScale()/2F+(this.direction[0] * MOVE_SPEED) / this.world.getWorldScale(),
                this.y-this.world.getWorldScale()/2F+(this.direction[1] * MOVE_SPEED) / this.world.getWorldScale(),
                (float)this.world.getWorldScale(),(float)this.world.getWorldScale());
        for (Wall w : world.getWalls()) {
            if (Intersector.overlaps(w.getBounds(),this.bounds)) {
                return false;
            }
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        if (timeSinceLaseAnimationChange > Player.ANIMATION_CHANGE_DELAY) {
            animationCycle+=animationDirection;
            if (animationCycle >= sprites.size() || animationCycle <= 0) {
                animationDirection = -animationDirection;
                animationCycle = (animationCycle>=sprites.size())?sprites.size()-2:0;
            }
            timeSinceLaseAnimationChange = 0;
        } else {
            timeSinceLaseAnimationChange += Gdx.graphics.getDeltaTime();
        }
        Sprite currentSprite = this.sprites.get(animationCycle);

        if (direction == Player.LEFT) currentSprite.setRotation(180);
        if (direction == Player.RIGHT) currentSprite.setRotation(0);
        if (direction == Player.UP) currentSprite.setRotation(270);
        if (direction == Player.DOWN) currentSprite.setRotation(90);

        batch.begin();
        currentSprite.setPosition(x- world.getWorldScale()/2F,y- world.getWorldScale()/2F);
        currentSprite.draw(batch);
        batch.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.bounds.getX(),this.bounds.getY(),this.bounds.getWidth(),this.bounds.getHeight());
        shapeRenderer.end();
    }

    public Integer getScore() {
        return this.score;
    }

    public void addScore(int i) {
        this.score += i;
    }

    public int getRelY() {
        return (int) relY;
    }

    public int getRelX() {
        return (int) relX;
    }

    public void dispose() {}

    public int[] getDirection() {
        return direction;
    }
}
