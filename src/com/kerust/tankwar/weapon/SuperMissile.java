package com.kerust.tankwar.weapon;

import com.kerust.tankwar.*;

import java.awt.*;

/* 超级导弹，可以同时向四个发射 */
public class SuperMissile implements Weapon {

    public static final int BULLET_SPEED = 15;
    public static final int BULLET_WIDTH = ResourceMgr.bullets[0].getWidth();
    public static final int BULLET_HEIGHT = ResourceMgr.bullets[0].getHeight();

    private Tank tank = null;
    private Group group = Group.BAD;
    private boolean isLiving = true;

    public int getX() { return 0; }
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return BULLET_WIDTH;
    }

    @Override
    public int getHeight() {
        return BULLET_HEIGHT;
    }

    @Override
    public void die() {}

    public Group getGroup() {
        return group;
    }

    @Override
    public void paint(Graphics graphics) {}

    private TankWarFrame tankWarFrame = null;
    private Direction direction = Direction.RIGHT;

    public SuperMissile(int pos_x, int pos_y, Direction direction, Group group, Tank tank, TankWarFrame tankWarFrame) {

        this.tank = tank;
        this.group = group;
        this.direction = direction;
        this.tankWarFrame = tankWarFrame;
        for (int i = 0; i < Direction.values().length; i ++) {
            this.tankWarFrame.addBullet(new Missile(pos_x, pos_y, Direction.values()[i], this.group, tank, this.tankWarFrame));
        }
    }
}