package com.lethan.pacman;

public class Blinky extends Ghost{
    public Blinky(World world, float x, float y) {
        super(world, x, y);
        this.mode = GhostAttackMode.ATTACK_MODE;
    }

}
