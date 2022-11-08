package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private static final int[] UP = new int[] {0,-1};
    private static final int[] DOWN = new int[] {0,1};
    private static final int[] RIGHT = new int[] {1,0};
    private static final int[] LEFT = new int[] {-1,0};
    private static final int[] IDLE = new int[] {0,0};
    private static final int[][] SUROUNDINGS = new int[][] {{}};

    private float y;
    private float x;
    private int[] direction;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private World world;
    private static float moveSpeed = 1F;
    private float relX, relY;
    private int[] lastDirection;
    private Rectangle bounds;

    public Player(World world, float x, float y) {
        world.setPlayer(this);
        this.world = world;
        this.x = x*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.y = y*world.getWorldScale()+this.world.getWorldScale()/2F;
        this.direction = new int[] {0,0};
        this.secondsBetweenMove = .01;
        this.lastMoveDeltaTime = 0;
        this.relX = x;
        this.relY = y;
        this.lastDirection = Player.IDLE;
        this.bounds = new Rectangle(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,(float)this.world.getWorldScale(),(float)this.world.getWorldScale());
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
            if (this.willNotCollide()) {
                lastMoveDeltaTime = 0;
                lastDirection = this.direction;
                this.x += this.direction[0] * moveSpeed;
                this.y += this.direction[1] * moveSpeed;
                this.relX += (this.direction[0] * moveSpeed) / this.world.getWorldScale();
                this.relY += (this.direction[1] * moveSpeed) / this.world.getWorldScale();
                this.bounds.set(this.x-this.world.getWorldScale()/2F,this.y-this.world.getWorldScale()/2F,(float)this.world.getWorldScale(),(float)this.world.getWorldScale());
            }
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public void getInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direction = Player.UP;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) direction = Player.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direction = Player.LEFT;
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) direction = Player.RIGHT;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) direction = Player.IDLE;
    }

    public boolean willNotCollide() {
        this.bounds.set(this.x-this.world.getWorldScale()/2F+(this.direction[0] * moveSpeed) / this.world.getWorldScale(),
                this.y-this.world.getWorldScale()/2F+(this.direction[1] * moveSpeed) / this.world.getWorldScale(),
                (float)this.world.getWorldScale(),(float)this.world.getWorldScale());
        for (Wall w : world.getWalls()) {
            if (Intersector.overlaps(w.getBounds(),this.bounds)) {
                return false;
            }
        }
        return true;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x,y, world.getWorldScale()/2F);
        shapeRenderer.end();
    }

    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.bounds.getX(),this.bounds.getY(),this.bounds.getWidth(),this.bounds.getHeight());
        shapeRenderer.end();
    }

}
