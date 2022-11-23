package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Inky extends Ghost{

    public Inky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    public static int[] rotate_point(float cx,float cy,float angle, int[] p) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);

        // translate point back to origin:
        p.x -= cx;
        p.y -= cy;

        // rotate point
        float xnew = p.x * c - p.y * s;
        float ynew = p.x * s + p.y * c;

        // translate point back:
        p.x = xnew + cx;
        p.y = ynew + cy;
        return p;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            float pcX = pacman.getRelX()+pacman.getDirection()[0]*2;
            float pcY = pacman.getRelY()+pacman.getDirection()[1]*2;
            targetX = (int)(-((relX-pcX))+pcX);
            targetY = (int)(-((relY-pcY))+pcY);

            moveTo((int)((-(relX-pcX))+pcX), (int)((-(relY-pcY))+pcY));
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
        }
    }

}