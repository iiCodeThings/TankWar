package com.kerust.tankwar;

import java.awt.*;

/* 普通子弹 */
public class Bullet implements Weapon{

    public static final int BULLET_SPEED = 15;
    public static final int BULLET_WIDTH = 10;
    public static final int BULLET_HEIGHT = 10;

    private int pos_x = 0;
    private int pos_y = 0;
    private Tank tank = null;
    private Group group = Group.BAD;
    private boolean isLiving = true;

    public int getX() {
        return pos_x;
    }

    public int getY() {
        return pos_y;
    }

    @Override
    public int getWidth() {
        return BULLET_WIDTH;
    }

    @Override
    public int getHeight() {
        return BULLET_HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void die() {
        isLiving = false;
    }

    private TankWarFrame tankWarFrame = null;
    private Direction direction = Direction.RIGHT;

    public Bullet(int pos_x, int pos_y, Direction direction, Group group, Tank tank, TankWarFrame tankWarFrame) {

        this.tank = tank;
        this.group = group;
        this.direction = direction;
        this.tankWarFrame = tankWarFrame;

        switch (direction) {
            case LEFT:
            case RIGHT:
                this.pos_x = pos_x + tank.getWidth() / 2 - BULLET_WIDTH / 2;
                this.pos_y = pos_y + tank.getHeight() / 2 - BULLET_HEIGHT / 2;
                break;
            case UP:
            case DOWN:
                this.pos_x = pos_x + tank.getWidth() / 2 - BULLET_HEIGHT / 2;
                this.pos_y = pos_y + tank.getHeight() / 2 - BULLET_WIDTH / 2;
                break;
            default:
                break;
        }

        if (group == Group.GOOD) {
            Sound.play_good_bullet_sound();
        } else {
            Sound.play_bad_bullet_sound();
        }
    }

    public void paint(Graphics graphics) {

        move();
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.fillOval(this.pos_x, this.pos_y, BULLET_WIDTH, BULLET_HEIGHT);
        graphics.setColor(color);
    }

    public void move() {

        if (! isLiving) {
            tankWarFrame.removeBullet(this);
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
            this.tankWarFrame.removeBullet(this);
        }
    }
}
