package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Inky extends Ghost{

    public Inky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    public static double[] rotatePoint(float cx,float cy,float angle, double[] p) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        p[0] -= cx;
        p[1] -= cy;
        double xnew = p[0] * c - p[1] * s;
        double ynew = p[0] * s + p[1] * c;
        p[0] = xnew + cx;
        p[1] = ynew + cy;
        return p;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            Ghost blinky = world.getBlinky();
            float pcX = pacman.getRelX();
            float pcY = pacman.getRelY();
            double[] rotatedPoint = rotatePoint(pacman.getRelX(), pacman.getRelY(), -100, new double[] {blinky.getRelX(), blinky.getRelY()});
            targetX = (float) rotatedPoint[0];
            targetY = (float) rotatedPoint[1];
            moveTo((int) targetX, (int) targetY);
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
        }
    }

}