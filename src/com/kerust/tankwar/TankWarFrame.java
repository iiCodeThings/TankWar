package com.kerust.tankwar;

import test.ImageBufferedTest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankWarFrame extends Frame {
    
    public static final int EXIT_SUCCESS = 0;
    public static int LAYOUT_WIDTH = 1920;
    public static int LAYOUT_HEIGHT = 1080;
    public static int MAX_HOSTILE_TANK_NUM = 8;
    public static int HOSTILE_TANK_ADD_STEP = 1;
    public static int INIT_HOSTILE_TANK_NUM = 5;
    public static final String TANKWAR_TITLE = "Tank War";

    private Random random = new Random();

    private Image offsetScreen = null;

    /* 爆炸效果 */
    private List<Explode> explodes = new ArrayList<>();

    /* 敌对坦克*/
    private List<Tank> hostileTanks = new ArrayList<>();

    /* 主战坦克的子弹*/
    private List<Bullet> mainTankBullets = new ArrayList<>();

    /* 主战坦克*/
    private Tank mainTank = new Tank(0, 100, Group.GOOD, this);

    /* 奖励 */
    private List<Award> awards = new ArrayList<>();

    static {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        LAYOUT_WIDTH = (int)(dimension.getWidth() * 0.9);
        LAYOUT_HEIGHT = (int)(dimension.getHeight() * 0.9);
    }
    public TankWarFrame() {
        this.setVisible(true);
        this.setTitle(TANKWAR_TITLE);
        this.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(EXIT_SUCCESS);
            }
        });

        initHostileTanks();
    }

    private void initHostileTanks() {
        for (int i = 0; i < INIT_HOSTILE_TANK_NUM; i ++) {
            Point point = new Point().getRandomPoint();
            Tank tank = new Tank(point.x, point.y, Group.BAD, this);
            tank.setMoving(true);
            hostileTanks.add(tank);
        }
    }

    private void addHostileTanks() {

        if (hostileTanks.size() >= MAX_HOSTILE_TANK_NUM) {
            return;
        }
        for (int i = 0; i < HOSTILE_TANK_ADD_STEP; i ++) {
            Point point = new Point().getRandomPoint();
            Tank tank = new Tank(point.x, point.y, Group.BAD, this);
            tank.setMoving(true);
            hostileTanks.add(tank);
        }
    }

    public void addAward(Award award) {
        this.awards.add(award);
    }

    public void removeAward(Award award) {
        this.awards.remove(award);
    }

    public void addHostileTank(Tank tank) {
        hostileTanks.add(tank);
    }

    public void removeHostileTank(Tank tank) {
        hostileTanks.remove(tank);
    }

    public void addExplode(Explode explode) {
        this.explodes.add(explode);
    }

    public void removeExplode(Explode explode) {
        this.explodes.remove(explode);
    }

    @Override
    public void update(Graphics g) {
        if (offsetScreen == null) {
            offsetScreen = createImage(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        }
        Graphics offsetGraphics = offsetScreen.getGraphics();
        //Color color = offsetGraphics.getColor();
        offsetGraphics.setColor(Color.BLACK);
        offsetGraphics.fillRect(0, 0, LAYOUT_WIDTH, LAYOUT_HEIGHT);
        //offsetGraphics.setColor(color);
        paint(offsetGraphics);
        g.drawImage(offsetScreen, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        showInfo(g);
        mainTank.paint(g);
        for (int i = 0; i < mainTankBullets.size(); i ++) {
            mainTankBullets.get(i).paint(g);
        }

        for(int i = 0; i < hostileTanks.size(); i ++) {
            hostileTanks.get(i).paint(g);
        }

        for (int i = 0; i < explodes.size(); i ++) {
            explodes.get(i).explode(g);
        }

        for (int i = 0; i < awards.size(); i ++) {
            awards.get(i).paint(g);
        }
        for (int i = 0; i < hostileTanks.size(); i ++) {
            Tank tank = hostileTanks.get(i);
            for (int j = 0; j < mainTankBullets.size(); j ++) {
                Bullet bullet = mainTankBullets.get(j);
                if (tank.isCollideWith(bullet)) {
                    tank.die();
                    bullet.die();
                    addExplode(new Explode(tank.getX(), tank.getY(), this));
                    addAward(new Award(tank.getX(), tank.getY(), tank.getAwardType(), this));
                    return;
                }
            }
        }

        /* 主战坦克吃奖励判断 */
        for (int i = 0; i < awards.size(); i ++) {
            Award award = awards.get(i);
            if (mainTank.isCollideWith(award)) {
                Sound.play_award_sound();
                award.die();
                if (award.getType() == Award.Type.MINE) {
                    /* 成功吃掉地雷会消灭屏幕上所有敌方坦克*/
                    for (int j = 0; j < hostileTanks.size(); j++) {
                        Tank tank = hostileTanks.get(j);
                        tank.die();
                        addExplode(new Explode(tank.getX(), tank.getY(), this));
                    }
                } else if (award.getType() == Award.Type.STAR) {
                    /* 成功吃掉星星会朝四个方向打子弹 */
                    mainTank.fireEverywhere();
                }
            }
        }
    }

    private void showInfo(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        graphics.drawString("Bullet Num: " + mainTankBullets.size(),5, 35);
        graphics.drawString("Tank  Num: " + hostileTanks.size(),5, 50);
        graphics.setColor(color);
    }

    public void addMainTankBullet(Bullet bullet) {
        mainTankBullets.add(bullet);
    }

    public void removeMainTankBullet(Bullet bullet) {
        mainTankBullets.remove(bullet);
    }

    class Point {
        int x;
        int y;

        public Point getRandomPoint() {
            Point point = new Point();
            point.x = (int)(Math.random() * LAYOUT_WIDTH);
            point.y = (int)(Math.random() * LAYOUT_HEIGHT);
            return point;
        }
    }

    class MyKeyListener implements KeyListener {

        private boolean bLeft = false;
        private boolean bUp = false;
        private boolean bRight = false;
        private boolean bDown = false;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    bLeft = true;
                    break;
                case KeyEvent.VK_UP:
                    bUp = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bRight = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bDown = true;
                    break;
                default:
                    break;
            }

            if (!bLeft && !bUp && !bRight && !bDown) {
                mainTank.setMoving(false);
            } else {
                mainTank.setMoving(true);
                if (bLeft) mainTank.setDirection(Direction.LEFT);
                if (bUp) mainTank.setDirection(Direction.UP);
                if (bRight) mainTank.setDirection(Direction.RIGHT);
                if (bDown) mainTank.setDirection(Direction.DOWN);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    bLeft = false;
                    break;
                case KeyEvent.VK_UP:
                    bUp = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bRight = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bDown = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    mainTank.fire();
                    break;
                case KeyEvent.VK_5:
                    addHostileTanks();
                    break;
                case KeyEvent.VK_P:
                    for (int i = 0; i < hostileTanks.size(); i ++) {
                        Tank tank = hostileTanks.get(i);
                        tank.setMoving(! tank.getMoving());
                    }
                default:
                    break;
            }

            if (!bLeft && !bUp && !bRight && !bDown) {
                mainTank.setMoving(false);
            } else {
                mainTank.setMoving(true);
                if (bLeft) mainTank.setDirection(Direction.LEFT);
                if (bUp) mainTank.setDirection(Direction.UP);
                if (bRight) mainTank.setDirection(Direction.RIGHT);
                if (bDown) mainTank.setDirection(Direction.DOWN);
            }
        }
    }
}
