package com.kerust.tankwar;

import java.awt.*;

public class Explode {

    public static final int WIDTH = ResourceMgr.explode[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explode[0].getHeight();

    private int x = 0;
    private int y = 0;
    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        //new Audio("src/images/audio/explode.wav").start();
    }

    public void explode(Graphics graphics) {
        step = (step + 1) % ResourceMgr.explode.length;
        int dx = x + Tank.TANK_WIDTH / 2 - WIDTH / 2;
        int dy = y + Tank.TANK_HEIGHT / 2 - HEIGHT / 2;
        graphics.drawImage(ResourceMgr.explode[step], dx, dy, null);
    }
}
