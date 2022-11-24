package com.lethan.pacman;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Blinky extends Ghost{
    public Blinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SCATTER_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SCATTER_MODE) {
            targetX = 24;
            targetY = -4;
            moveTo(24,-4);
        }
        if (mode == GhostAttackMode.SICKO_MODE) {
            targetX = this.world.getPlayer().getRelX();
            targetY = this.world.getPlayer().getRelY();
            moveTo((int) targetX, (int) targetY);
        }

    }
}
