package com.lethan.pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//https://gamedev.stackexchange.com/questions/197165/java-simple-2d-grid-pathfinding
//All credit to https://gamedev.stackexchange.com/users/26037/bornander
// I will implement my own code eventually, but currently my version of A* runs too slow.

public class PathfindingEngine {

    int[][] map;

    public PathfindingEngine(int[][] arr) {
        map = arr;
    }

    public static class Point {
        public int x;
        public int y;
        public Point previous;

        public Point(int x, int y, Point previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }

        @Override
        public String toString() { return String.format("(%d, %d)", x, y); }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        public Point offset(int ox, int oy) { return new Point(x + ox, y + oy, this);  }
    }

    public static boolean IsWalkable(int[][] map, Point point) {
        if (point.y < 0 || point.y > map.length - 1) return false;
        if (point.x < 0 || point.x > map[0].length - 1) return false;
        return map[point.y][point.x] == 0;
    }

    public static List<Point> FindNeighbors(int[][] map, Point point) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
        if (IsWalkable(map, up)) neighbors.add(up);
        if (IsWalkable(map, down)) neighbors.add(down);
        if (IsWalkable(map, left)) neighbors.add(left);
        if (IsWalkable(map, right)) neighbors.add(right);
        return neighbors;
    }

    public List<Point> getPath(int sX, int sY, int eX, int eY) {
        Point start = new Point(sX, sY, null);
        Point end = new Point(eX, eY, null);
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Point point = used.get(i);
                for (Point neighbor : FindNeighbors(this.map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }
            for(Point point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }
            if (!finished && newOpen.isEmpty())
                return null;
        }
        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }
}