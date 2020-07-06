package com.kerust.tankwar;

import java.awt.*;

public class Explode {

    private int x = 0;
    private int y = 0;
    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void explode(Graphics graphics) {
        step = (step + 1) % ResourceMgr.explode.length;
        graphics.drawImage(ResourceMgr.explode[step], x, y, null);
    }
}
