package com.lethan.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Inky extends Ghost{

    public Inky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            float pcX = pacman.getRelX()+pacman.getDirection()[0]*2;
            float pcY = pacman.getRelY()+pacman.getDirection()[1]*2;
            moveTo((int)((-(relX-pcX))+pcX), (int)((-(relY-pcY))+pcY));
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
        }
    }

}