package com.kerust.tankwar;

import java.awt.*;

/* 恐龙导弹，发射出一只恐龙 */
public class Dinosaur implements Weapon {

    public static final int BULLET_SPEED = 15;
    private static final int BULLET_WIDTH = ResourceMgr.kl_bullets[0].getWidth();
    private static final int BULLET_HEIGHT = ResourceMgr.kl_bullets[0].getHeight();

    private int pos_x = 0;
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

    private int pos_y = 0;
    private TankWarFrame tankWarFrame = null;
    private Direction direction = Direction.RIGHT;

    public Dinosaur(int pos_x, int pos_y, Direction direction, Group group, Tank tank, TankWarFrame tankWarFrame) {

        this.tank = tank;
        this.group = group;
        this.direction = direction;

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
        this.tankWarFrame = tankWarFrame;

        if (group == Group.GOOD) {
            Sound.play_good_bullet_sound();
        } else {
            Sound.play_bad_bullet_sound();
        }
    }

    public void paint(Graphics graphics) {

        move();

        switch (direction) {
            case LEFT:
                graphics.drawImage(ResourceMgr.kl_bullets[Direction.LEFT.ordinal()], pos_x, pos_y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.kl_bullets[Direction.UP.ordinal()], pos_x, pos_y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.kl_bullets[Direction.RIGHT.ordinal()], pos_x, pos_y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.kl_bullets[Direction.DOWN.ordinal()], pos_x, pos_y, null);
                break;
            default:
                break;
        }
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

    public void die() {
        isLiving = false;
    }
}
