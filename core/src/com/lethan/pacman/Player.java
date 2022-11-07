package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {

    private float y;
    private float x;
    private static final int[] UP = new int[] {0,-1};
    private static final int[] DOWN = new int[] {0,1};
    private static final int[] RIGHT = new int[] {1,0};
    private static final int[] LEFT = new int[] {-1,0};
    private int[] direction;
    private float lastMoveDeltaTime;
    private double secondsBetweenMove;
    private World world;
    private static float moveSpeed = 0.1F;

    public Player(World world, float x, float y) {
        world.setPlayer(this);
        this.world = world;
        this.x = x;
        this.y = y;
        this.direction = new int[] {0,0};
        this.secondsBetweenMove = .001;
        this.lastMoveDeltaTime = 0;
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
            lastMoveDeltaTime = 0;
            this.x += this.direction[0]*moveSpeed;
            this.y += this.direction[1]*moveSpeed;
        } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
    }

    public void getInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direction = Player.UP;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) direction = Player.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direction = Player.LEFT;
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) direction = Player.RIGHT;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(this.x*world.getWorldScale()+this.world.getWorldScale()/2F,
                this.y*world.getWorldScale()+this.world.getWorldScale()/2F, world.getWorldScale()/2F);
        shapeRenderer.end();
    }

}
