package com.kerust.tankwar;

import java.awt.*;

public class Explode {

    /* 选最大图片的长和宽 */
    public static final int WIDTH = 256; //ResourceMgr.explode[0].getWidth();
    public static final int HEIGHT = 256; //ResourceMgr.explode[0].getHeight();

    private int x = 0;
    private int y = 0;
    private int step = 0;
    private TankWarFrame tankWarFrame = null;

    public Explode(int x, int y, TankWarFrame tankWarFrame) {
        this.x = x;
        this.y = y;
        this.tankWarFrame = tankWarFrame;
        Sound.play_explode_sound();
    }

    public void explode(Graphics graphics) {
        int dx = x + Tank.TANK_WIDTH / 2 - WIDTH / 2;
        int dy = y + Tank.TANK_HEIGHT / 2 - HEIGHT / 2;
        graphics.drawImage(ResourceMgr.explode[step ++], dx, dy, null);
        if (step >= ResourceMgr.explode.length) {
            tankWarFrame.removeExplode(this);
        }
    }
}
