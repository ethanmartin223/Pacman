package com.lethan.pacman;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pinky extends Ghost{
    public Pinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SCATTER_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            int[] dir = pacman.getDirection();
            targetY = pacman.getRelY() + dir[1] * 4;
            targetX = pacman.getRelX() + dir[0] * 4;
            moveTo(pacman.getRelX() + dir[0] * 4, (pacman.getRelY() + dir[1] * 4));
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
            targetX = 3;
            targetY = -4;
        }
        if (mode == GhostAttackMode.FRIGHTENED) {
            moveRandom();
        }
    }
}