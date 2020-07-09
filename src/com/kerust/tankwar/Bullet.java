package com.kerust.tankwar;

import java.awt.*;

public interface Bullet {

    public int getX();
    public int getY();

    public int getWidth();
    public int getHeight();

    public Group getGroup();

    public void paint(Graphics graphics);

    public void die();
}
