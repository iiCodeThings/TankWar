package com.kerust.tankwar;

import java.awt.*;

/* 生成随机奖励 */
public class Award {

    public static final int WIDTH = ResourceMgr.mines[0].getWidth();
    public static final int HEIGHT = ResourceMgr.mines[0].getHeight();

    private int x = 0;
    private int y = 0;
    Type type = Type.MINE;
    int step = 0;
    private boolean isLiving = true;
    private TankWarFrame tankWarFrame;

    public Award(int x, int y, Type type, TankWarFrame tankWarFrame) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.tankWarFrame = tankWarFrame;
    }

    public int getWidth() {
        if (type == Type.STAR) {
            return ResourceMgr.stars[0].getWidth();
        } else if (type == Type.MINE) {
            return ResourceMgr.mines[0].getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (type == Type.STAR) {
            return ResourceMgr.stars[0].getHeight();
        } else if (type == Type.MINE) {
            return ResourceMgr.mines[0].getHeight();
        }
        return 0;
    }

    public Type getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void paint(Graphics graphics) {

        if (! isLiving) {
            this.tankWarFrame.removeAward(this);
            return;
        }

        if (step++ > 8 * GameController.BASE) {
            this.tankWarFrame.removeAward(this);
            return;
        }

        if (this.type == Type.MINE) {
            graphics.drawImage(ResourceMgr.mines[step % ResourceMgr.mines.length], this.x, this.y, null);
        } else if (this.type == Type.STAR) {
            graphics.drawImage(ResourceMgr.stars[step % ResourceMgr.stars.length], this.x, this.y, null);
        }
    }

    public void die() {
        isLiving = false;
    }

    enum Type {
        STAR, /* 吃了武器会变厉害 */
        MINE /* 地雷： 吃了会炸掉屏幕上所有敌方坦克 */
    }
}
