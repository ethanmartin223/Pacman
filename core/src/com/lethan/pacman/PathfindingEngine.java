package com.lethan.pacman;

import java.lang.Math;
import java.util.*;

public class PathfindingEngine {

    int[][] array;
    public static int[][] validMoves = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public PathfindingEngine(int[][] arrData) {
        array = arrData;
    }

    public int getSpaceScore(int sX, int sY, int eX, int eY, int fX, int fY) {
        int hScore = getDistanceBetween(sX, sY, fX, fY) * 10;
        int gScore = getDistanceBetween(fX, fY, eX, eY) * 10;
        return gScore + hScore;
    }

    public int getDistanceBetween(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public List<int[]> getPath(int sX, int sY, int eX, int eY) {
        List<int[]> path = new ArrayList<int[]>();
        List<int[]> visited = new ArrayList<int[]>();
        path.add(new int[]{sX, sY});
        visited.add(new int[]{sX, sY});

        int currentX = sX;
        int currentY = sY;
        int lowestKey;
        Map<Integer, int[]> currentScores;
        currentScores = new HashMap<>();

        while (currentX != eX && currentY != eY) {
            currentScores.clear();
            System.out.println(Arrays.deepToString(path.toArray()));
            for (int[] direction : validMoves) {
                if (currentY + direction[1] == eY && currentX + direction[0] == eX) {
                    path.add(new int[]{currentX + direction[0], currentY + direction[1]});
                    return path;
                }
                if (
                        ((currentY + direction[1]) > -1) &&
                                ((currentX + direction[0]) > -1) &&
                                ((currentY + direction[1]) < (array.length)) &&
                                ((currentX + direction[0]) < array[0].length) &&
                                ((array[currentY + direction[1]][currentX + direction[0]] == 0))
                ) {
                    boolean found = false;
                    System.out.println("gets here");
                    for (int[] x : visited) {
                        if ((x[1] == (currentY + direction[1])) && (x[0] == (currentX + direction[0]))) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        currentScores.put(
                                getSpaceScore(currentX, currentY, eX, eY, currentX + direction[0], currentY + direction[1]),
                                new int[]{currentX + direction[0], currentY + direction[1]});
                    }
                }
            }
            if (currentScores.size() > 0) {
                lowestKey = Collections.min(currentScores.keySet());
                currentX = currentScores.get(lowestKey)[0];
                currentY = currentScores.get(lowestKey)[1];
                visited.add(new int[]{currentX, currentY});
                path.add(new int[]{currentX, currentY});
            } else {
                path.remove(path.size() - 1);
                currentX = path.get(path.size() - 1)[0];
                currentY = path.get(path.size() - 1)[1];
            }
        }
        return path;
    }
}
