package com.lethan.pacman;

public class Blinky extends Ghost{
    public Blinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.ATTACK_MODE;
    }

    @Override
    public void update() {
        switch (mode) {
            case IDLE_MODE:;
            case DEATH_MODE:;
            case ATTACK_MODE:
                moveTo(this.world.getPlayer().getRelX(), this.world.getPlayer().getRelY());
            case SCATTER_MODE:;
            case POWER_PELLET_MODE:;
        }
    }
}
