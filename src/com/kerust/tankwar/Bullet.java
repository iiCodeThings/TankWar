package com.kerust.tankwar;

import java.awt.*;

public class Bullet {

    public static final int BULLET_SPEED = 15;
    public static final int BULLET_WIDTH = ResourceMgr.bulletL.getWidth();
    public static final int BULLET_HEIGHT = ResourceMgr.bulletL.getHeight();

    private int pos_x = 0;
    private Group group = Group.BAD;
    private boolean isLiving = true;

    public int getX() {
        return pos_x;
    }

    public int getY() {
        return pos_y;
    }

    public Group getGroup() {
        return group;
    }

    private int pos_y = 0;
    private TankWarFrame tankWarFrame = null;
    private Direction direction = Direction.RIGHT;

    public Bullet(int pos_x, int pos_y, Direction direction, Group group, TankWarFrame tankWarFrame) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.group = group;
        this.direction = direction;
        this.tankWarFrame = tankWarFrame;
    }

    public void paint(Graphics graphics) {

        move();

        switch (direction) {
            case LEFT:
                graphics.drawImage(ResourceMgr.bulletL, pos_x, pos_y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.bulletU, pos_x, pos_y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.bulletR, pos_x, pos_y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.bulletD, pos_x, pos_y, null);
                break;
            default:
                break;
        }
    }

    public void move() {

        if (! isLiving) {
            tankWarFrame.removeMainTankBullet(this);
            return;
        }

        switch (direction) {
            case LEFT:
                pos_x -= BULLET_SPEED;
                break;
            case UP:
                pos_y -= BULLET_SPEED;
                break;
            case RIGHT:
                pos_x += BULLET_SPEED;
                break;
            case DOWN:
                pos_y += BULLET_SPEED;
                break;
            default:
                break;
        }

        if (pos_x < 0 || pos_y < 0 || pos_x > TankWarFrame.LAYOUT_WIDTH || pos_y > TankWarFrame.LAYOUT_HEIGHT) {
            this.tankWarFrame.removeMainTankBullet(this);
        }
    }

    public void die() {
        isLiving = false;
    }
}
