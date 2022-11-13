package com.lethan.pacman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Ghost {
    private World world;
    private int relX, relY;
    private float x,y;
    private Map<String, Sprite> sprites;
    private String currentSprite;
    private final String name;

    //const
    private static final int[][] VALID_MOVES = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public Ghost(World world, float x, float y) {
        this.world = world;
        this.relX = (int)x;
        this.relY = (int)y;
        this.y = y;
        this.x = x;
        this.sprites = null;
        this.name = this.getClass().getSimpleName().toLowerCase();
        this.currentSprite = name+"_idle";
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        sprites.get(currentSprite).draw(spriteBatch);
        spriteBatch.end();
    }

    private void moveToPosition(int x, int y) {

    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public List<int[]> getPath(int x, int y, int destX, int destY) {
        int[][] l = world.getLayout();
        Map<Integer, int[]> nextAvailableMoves = new HashMap<>();
        List<int[]> path = new ArrayList<>();
        int algX = x;
        int algY = y;
        int bestScore;

        while ((algX != destX) && (algY != destY)) {
            for (int[] item : Ghost.VALID_MOVES) {
                if ((item[0] + algX < l[0].length) && (item[0] + algX >= 0) &&
                        (item[1] + algY < l.length) && (item[1] + algY >= 0) &&
                        (l[item[1] + algY][item[0] + algX] == 0))
                    nextAvailableMoves.put(
                            getSpaceScore(item[0] + algX, item[1] + algY,algX,algY,destX,destY),
                            new int[]{item[0] + algX, item[1] + algY}
                    );
            }
            if (nextAvailableMoves.size() <= 0) return path;
            bestScore = Integer.MAX_VALUE;
            for (Integer key : nextAvailableMoves.keySet()) if (key < bestScore) bestScore = key;
            algX = nextAvailableMoves.get(bestScore)[0];
            algY = nextAvailableMoves.get(bestScore)[1];
            path.add(new int[] {algX, algY});
        }
        return path;
    }

    // a* functions below here, used in getPath
    private double coordinateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    // toX-Y is final dest, ie pacman's x,y
    // fromX-Y is the current pos of the search algorithm
    // spaceX-Y is the space you want to check.
    private int getSpaceScore(int spaceX, int spaceY, int fromX, int fromY, int toX, int toY) {
        double hScore = this.coordinateDistance(fromX,fromY,toX,toY)*10;
        double gScore = this.coordinateDistance(spaceX, spaceY, toX, toY)*10;
        return (int)(hScore + gScore);
    }

    private <T extends Comparable<T>> T min(List<T> list) {return Collections.min(list);}
}
