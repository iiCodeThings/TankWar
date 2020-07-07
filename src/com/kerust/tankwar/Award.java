package com.kerust.tankwar;

import java.awt.*;
import java.util.Random;

/* 生成随机奖励 */
public class Award {

    public static final int WIDTH = ResourceMgr.mines[0].getWidth();
    public static final int HEIGHT = ResourceMgr.mines[0].getHeight();

    int step = 0;
    private int x = 0;
    private int y = 0;
    Type type = Type.MINE;
    private boolean isLiving = true;
    private static Random random = new Random();
    private TankWarFrame tankWarFrame = null;

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
        } else if (type == Type.TANK) {
            return ResourceMgr.awardTanks[0].getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (type == Type.STAR) {
            return ResourceMgr.stars[0].getHeight();
        } else if (type == Type.MINE) {
            return ResourceMgr.mines[0].getHeight();
        } else if (type == Type.TANK) {
            return ResourceMgr.awardTanks[0].getHeight();
        }
        return 0;
    }

    public static Type getRandomAwardType() {
        if (true)
            return Type.TANK;
        /* 50%的坦克没有奖励 */
        /* 30%的坦克有星星奖励 */
        /* 10%的坦克有地雷奖励 */
        /* 10%的坦克有加命奖励 */
        int num = random.nextInt(100);
        if (num >= 0 && num <= 49) {
            return Type.NONE;
        } else if (num >= 50 && num <= 79) {
            return Type.STAR;
        } else if (num >= 80 && num <= 89) {
            return Type.MINE;
        } else if (num >= 90 && num <= 99) {
            return Type.TANK;
        }
        return Type.NONE;
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

        if (this.type == Type.NONE) {
            return;
        }

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
        } else if (this.type == Type.TANK) {
            graphics.drawImage(ResourceMgr.awardTanks[step % ResourceMgr.awardTanks.length], this.x, this.y, null);
        }
    }

    public void die() {
        isLiving = false;
    }

    enum Type {
        NONE, /* 无任何奖励 */
        STAR, /* 吃了武器会变厉害 */
        MINE, /* 地雷： 吃了会炸掉屏幕上所有敌方坦克 */
        TANK /* 吃了增加一条命 */
    }
}
