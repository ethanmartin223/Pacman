package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Inky extends Ghost{

    public Inky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SCATTER_MODE;
    }

    public static double[] rotatePoint(float cx,float cy,float angle, float pointX,float pointY) {
        double angleInRadians = angle * Math.PI / 180;
        double lineLength = Math.sqrt(Math.pow((cx-pointX),2)+Math.pow((cy-pointY),2));
        double endX = cx + lineLength * Math.cos(angleInRadians);
        double endY = cy + lineLength * Math.sin(angleInRadians);
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
    }

}