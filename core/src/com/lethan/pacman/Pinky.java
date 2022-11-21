package com.lethan.pacman;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pinky extends Ghost{
    public Pinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            int[] dir = pacman.getDirection();
            moveTo(pacman.getRelX() + dir[0] * 4, (pacman.getRelY() + dir[1] * 4));
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
        }
    }
}