package com.lethan.pacman;

public class Blinky extends Ghost{
    public Blinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.SICKO_MODE;
    }

    @Override
    public void update() {
        if (mode == GhostAttackMode.SCATTER_MODE) moveTo(24,-4);
        if (mode == GhostAttackMode.SICKO_MODE) moveTo(this.world.getPlayer().getRelX(), this.world.getPlayer().getRelY());

    }
}
