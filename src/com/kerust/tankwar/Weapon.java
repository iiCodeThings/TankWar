package com.kerust.tankwar;

import java.awt.*;

/* 武器的抽象类 */
public interface Weapon {

    public int getX();
    public int getY();

    public int getWidth();
    public int getHeight();

    public void die();
    public Group getGroup();

    public void paint(Graphics graphics);

    enum Type {
        BULLET, /* 普通子弹 */
        MISSILE, /* 导弹 */
        DINOSAUR, /* 恐龙导弹，发射出一只恐龙 */
        SUPER_MISSILE /* 超级导弹，可以向四个方向同时发射导弹 */
    }
}
