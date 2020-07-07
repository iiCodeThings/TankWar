package com.kerust.tankwar;

import java.awt.*;
import java.util.Random;

public class Tank {

    public static final int TANK_SPEED = 5;
    public static final int TANK_WIDTH = ResourceMgr.tankD.getWidth();
    public static final int TANK_HEIGHT = ResourceMgr.tankD.getHeight();

    private int pos_x = 0;
    private int pos_y = 0;
    private Group group = Group.BAD;
    private boolean isMoving = false;
    private boolean isLiving = true;
    private TankWarFrame tankWarFrame = null;
    private Direction direction = Direction.RIGHT;
    private Random random = new Random();

    /* 敌方坦克携带的奖励，打死后可以显示奖励 */
    private Award.Type awardType = null;

    public Tank(int pos_x, int pos_y, Group group, TankWarFrame tankWarFrame) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.group = group;
        awardType = Award.Type.STAR;
        this.tankWarFrame = tankWarFrame;
    }

    public int getX() {
        return pos_x;
    }

    public Award.Type getAwardType() {
        return this.awardType;
    }

    public int getY() {
        return pos_y;
    }

    public void paint(Graphics graphics) {

        move();

        switch (direction) {
            case LEFT:
                graphics.drawImage(ResourceMgr.tankL, pos_x, pos_y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.tankU, pos_x, pos_y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.tankR, pos_x, pos_y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.tankD, pos_x, pos_y, null);
                break;
            default:
                break;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {

        if (! isLiving) {
            tankWarFrame.removeHostileTank(this);
            return;
        }
        if (! isMoving) {
            return;
        }

        switch (direction) {
            case LEFT:
                pos_x -= (group == Group.GOOD ? TANK_SPEED * 2 : TANK_SPEED);
                break;
            case UP:
                pos_y -= (group == Group.GOOD ? TANK_SPEED * 2 : TANK_SPEED);
                break;
            case RIGHT:
                pos_x += (group == Group.GOOD ? TANK_SPEED * 2 : TANK_SPEED);
                break;
            case DOWN:
                pos_y += (group == Group.GOOD ? TANK_SPEED * 2 : TANK_SPEED);
                break;
            default:
                break;
        }

        if (group == Group.BAD) {

            if (random.nextInt(1000) > 990) {
                this.fire();
            }

            if (pos_x < 0 || pos_y < 0 || pos_x > TankWarFrame.LAYOUT_WIDTH - TANK_WIDTH
                    || pos_y > TankWarFrame.LAYOUT_HEIGHT - TANK_HEIGHT || random.nextInt(100) > 95) {
                /* 碰到边缘，或者走一会儿就随机改变方向 */
                setDirection(Direction.values()[random.nextInt(4)]);
            }
        }
        if (pos_x < 0) pos_x = 0;
        if (pos_y < 0) pos_y = 0;
        if (pos_x > TankWarFrame.LAYOUT_WIDTH - TANK_WIDTH) pos_x = TankWarFrame.LAYOUT_WIDTH - TANK_WIDTH;
        if (pos_y > TankWarFrame.LAYOUT_HEIGHT - TANK_HEIGHT) pos_y = TankWarFrame.LAYOUT_HEIGHT - TANK_HEIGHT;
    }

    public boolean isCollideWith(Bullet bullet) {

        if (this.group == bullet.getGroup()) {
            return false;
        }

        Rectangle tankRect = new Rectangle(this.pos_x, this.pos_y, TANK_WIDTH, TANK_HEIGHT);
        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), bullet.BULLET_WIDTH, bullet.BULLET_HEIGHT);
        return tankRect.intersects(bulletRect);
    }

    public void die() {
        isLiving = false;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public boolean getMoving() { return this.isMoving; }

    public void fireEverywhere() {
        int x, y;

        x = this.pos_x + TANK_WIDTH / 2 - Bullet.BULLET_WIDTH / 2;
        y = this.pos_y + TANK_HEIGHT / 2 - Bullet.BULLET_HEIGHT / 2;
        this.tankWarFrame.addMainTankBullet(new Bullet(x, y, Direction.LEFT, this.group, this.tankWarFrame));
        this.tankWarFrame.addMainTankBullet(new Bullet(x, y, Direction.RIGHT, this.group, this.tankWarFrame));

        x = this.pos_x + TANK_WIDTH / 2 - Bullet.BULLET_HEIGHT / 2;
        y = this.pos_y + TANK_HEIGHT / 2 - Bullet.BULLET_WIDTH / 2;
        this.tankWarFrame.addMainTankBullet(new Bullet(x, y, Direction.UP, this.group, this.tankWarFrame));
        this.tankWarFrame.addMainTankBullet(new Bullet(x, y, Direction.DOWN, this.group, this.tankWarFrame));
    }

    public void fire() {

        int x, y;

        switch (direction) {
            case LEFT:
            case RIGHT:
                x = this.pos_x + TANK_WIDTH / 2 - Bullet.BULLET_WIDTH / 2;
                y = this.pos_y + TANK_HEIGHT / 2 - Bullet.BULLET_HEIGHT / 2;
                this.tankWarFrame.addMainTankBullet(new Bullet(x, y, this.direction, this.group, this.tankWarFrame));
                break;
            case UP:
            case DOWN:
                x = this.pos_x + TANK_WIDTH / 2 - Bullet.BULLET_HEIGHT / 2;
                y = this.pos_y + TANK_HEIGHT / 2 - Bullet.BULLET_WIDTH / 2;
                this.tankWarFrame.addMainTankBullet(new Bullet(x, y, this.direction, this.group, this.tankWarFrame));
                break;
            default:
                break;
        }
    }

    public boolean isCollideWith(Award award) {
        Rectangle tankRect = new Rectangle(this.pos_x, this.pos_y, TANK_WIDTH, TANK_HEIGHT);
        Rectangle awardRect = new Rectangle(award.getX(), award.getY(), award.getWidth(), award.getHeight());
        return tankRect.intersects(awardRect);
    }
}
