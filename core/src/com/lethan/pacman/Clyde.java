package com.lethan.pacman;

public class Clyde extends Ghost{

    public Clyde(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SICKO_MODE) {
            Player pacman = world.getPlayer();
            if (Math.abs(pacman.getRelX()-relX)+Math.abs(pacman.getRelY()-relY) > 8) {
                moveTo(pacman.getRelX(), pacman.getRelY());
                targetX = pacman.getRelX();
                targetY = pacman.getRelY();
            } else {
                moveTo(1,31);
                targetX = 1;
                targetY = 31;
            }
        }
        if (mode == GhostAttackMode.SCATTER_MODE) {
            moveTo(3,-4);
        }
    }

}
