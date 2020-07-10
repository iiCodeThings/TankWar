package com.kerust.tankwar;

import java.awt.*;
import java.util.Random;

/* 生成随机奖励 */
public interface Award {

    public int getWidth();

    public int getHeight();

    public Type getType();

    public int getX();

    public int getY();

    public void paint(Graphics graphics);

    public void die();

    enum Type {
        NONE, /* 无任何奖励 */
        STAR, /* 吃了武器会变厉害 */
        MINE, /* 地雷： 吃了会炸掉屏幕上所有敌方坦克 */
        TANK, /* 吃了增加一条命 */
        DINOSAUR /* 吃了会发射恐龙子弹 */
    }

    public static Type getRandomAwardType() {
        /* 40%的坦克没有奖励 */
        /* 10%的坦克有恐龙子弹奖励 */
        /* 30%的坦克有星星奖励 */
        /* 10%的坦克有地雷奖励 */
        /* 10%的坦克有加命奖励 */
        int num = new Random().nextInt(100);
        if (num >= 0 && num <= 39) {
            return Type.NONE;
        } else if (num >= 40 && num<= 49) {
            return Type.DINOSAUR;
        } else if (num >= 50 && num <= 79) {
            return Type.STAR;
        } else if (num >= 80 && num <= 89) {
            return Type.MINE;
        } else if (num >= 90 && num <= 99) {
            return Type.TANK;
        }
        return Type.NONE;
    }
}
