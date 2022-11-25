package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Inky extends Ghost{

    public Inky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SCATTER_MODE;
    }

    public static double[] rotatePoint(float cx,float cy, float pointX,float pointY) {
        double endX = pointX-cx;
        double endY = pointY-cy;
        endX *= -1;
        endY *= -1;
        endX += cx;
        endY += cy;
        return new double[] {endX, endY};
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            Ghost blinky = world.getBlinky();
            float pcX = pacman.getRelX();
            float pcY = pacman.getRelY();
            double[] rotatedPoint = rotatePoint(pacman.getRelX()+pacman.getDirection()[0]*2,
                    pacman.getRelY()+pacman.getDirection()[1]*2,
                    blinky.getRelX(), blinky.getRelY());
            targetX = (float) rotatedPoint[0];
            targetY = (float) rotatedPoint[1];
            moveTo((int) targetX, (int) targetY);
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(31,32);
            targetX = 31;
            targetY =32;
        }
        if (mode == GhostAttackMode.FRIGHTENED) {
            moveRandom();
        }
    }

}