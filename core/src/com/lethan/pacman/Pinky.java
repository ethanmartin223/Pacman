package com.lethan.pacman;

public class Pinky extends Ghost{
    public Pinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.ATTACK_MODE;
    }

    @Override
    public void update() {
        switch (mode) {
            case IDLE_MODE:;
            case DEATH_MODE:;
            case ATTACK_MODE:
                Player pacman = world.getPlayer();
                int[] dir = pacman.getDirection();
                moveTo(pacman.getRelX()+dir[0]*4, pacman.getRelY()+dir[1]*4);
            case SCATTER_MODE:;
            case POWER_PELLET_MODE:;
        }
    }
}