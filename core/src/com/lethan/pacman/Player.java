package com.lethan.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move() {
        this.getInput();
        if (this.willNotCollide()) {
            if (lastMoveDeltaTime >= secondsBetweenMove) {
                lastMoveDeltaTime = 0;
                this.x += this.direction[0] * moveSpeed;
                this.y += this.direction[1] * moveSpeed;
                this.relX += (this.direction[0] * moveSpeed) / this.world.getWorldScale();
                this.relY += (this.direction[1] * moveSpeed) / this.world.getWorldScale();
            } else lastMoveDeltaTime += Gdx.graphics.getDeltaTime();
        }
    }

    public void getInput() {
        lastDirection = this.direction;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direction = Player.UP;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) direction = Player.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direction = Player.LEFT;
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) direction = Player.RIGHT;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) direction = Player.IDLE;
    }

    public boolean willNotCollide() {
        int[][] l = world.getLayout();
        if (direction != Player.IDLE) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    System.out.print(l[(int) relY + y][(int) relX + x]);
                }
                System.out.println();
            }
            System.out.println("\n");
            System.out.println("relX, relY: "+relX+", "+relY);
            System.out.println("relX-(int)relX: "+(relX - (int)relX)+"  |  relY-(int)relY: "+(relY - (int)relY));
        }
        if ((direction == Player.DOWN && l[(int) (relY + 1)][(int) relX] == 1) ||
            (direction == Player.UP && l[(int) (relY - 1)][(int) (relX)] == 1) ||
            (direction == Player.LEFT && l[(int) relY][(int) (relX - 1)] == 1) ||
            (direction == Player.RIGHT && l[(int) relY][(int) (relX + 1)] == 1)) {

            this.direction=lastDirection;
            return false;
        }
        return true;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x,y, world.getWorldScale()/2F);
        shapeRenderer.end();
    }

}
