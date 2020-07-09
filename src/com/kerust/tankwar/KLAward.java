package com.kerust.tankwar;

import java.awt.*;

/* 生成随机奖励 */
public class KLAward implements Award {

    int step = 0;
    private int x = 0;
    private int y = 0;
    Type type = Type.MINE;
    private boolean isLiving = true;
    private TankWarFrame tankWarFrame = null;

    public KLAward(int x, int y, TankWarFrame tankWarFrame) {
        this.x = x;
        this.y = y;
        this.tankWarFrame = tankWarFrame;
    }

    public int getWidth() {
        return ResourceMgr.kl[0].getWidth();
    }

    public int getHeight() {
        return ResourceMgr.kl[0].getHeight();
    }

    public Award.Type getType() {
        return Award.Type.KL;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void die() {
        isLiving = false;
    }

    public void paint(Graphics graphics) {

        if (! isLiving) {
            this.tankWarFrame.removeAward(this);
            return;
        }

        /* 奖励大概停留8s后自动消失 */
        if (step > 8 * tankWarFrame.BASE) {
            this.tankWarFrame.removeAward(this);
            return;
        }

        graphics.drawImage(ResourceMgr.kl[step++ % ResourceMgr.kl.length], this.x, this.y, null);
    }
}
