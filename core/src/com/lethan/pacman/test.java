package com.lethan.pacman;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[][] map = new int[][] {
                {0,0,0,1,0,0,0},
                {0,0,0,1,0,0,0},
                {1,0,1,1,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
        };

        PathfindingEngine pathfinder = new PathfindingEngine(map);

        for (int[] c : pathfinder.getPath(0,0,6,6)) {
            map[c[1]][c[0]] = 2;
        }

        for (int y=0; y < map.length; y++) {
            for (int x=0; x < map[y].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }
}
