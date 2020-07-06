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
    public static final int LAYOUT_WIDTH = 960;
    public static final int LAYOUT_HEIGHT = 540;
    public static final String TANKWAR_TITLE = "Tank War";

    private Random random = new Random();

    private Image offsetScreen = null;

    /* 敌对坦克*/
    private List<Tank> hostileTanks = new ArrayList<>();

    /* 主战坦克的子弹*/
    private List<Bullet> mainTankBullets = new ArrayList<>();

    /* 主战坦克*/
    private Tank mainTank = new Tank(0, 100, Group.GOOD, this);

    public TankWarFrame() {
        this.setTitle(TANKWAR_TITLE);
        this.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(EXIT_SUCCESS);
            }
        });

        this.addKeyListener(new MyKeyListener());

        for (int i = 0; i < 5; i ++) {
            Point point = new Point().getRandomPoint();
            Tank tank = new Tank(point.x, point.y, Group.BAD, this);
            tank.setMoving(true);
            hostileTanks.add(tank);
        }

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < hostileTanks.size(); i++) {
                        hostileTanks.get(i).fire();
                    }
                    try {
                        Thread.sleep(random.nextInt(5) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void addHostileTank(Tank tank) {
        hostileTanks.add(tank);
    }

    public void removeHostileTank(Tank tank) {
        hostileTanks.remove(tank);
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

        for (int i = 0; i < hostileTanks.size(); i ++) {
            Tank tank = hostileTanks.get(i);
            for (int j = 0; j < mainTankBullets.size(); j ++) {
                Bullet bullet = mainTankBullets.get(j);
                if (tank.isCollideWith(bullet)) {
                    tank.die();
                    bullet.die();
                    new Explode(tank.getX(), tank.getY()).explode(g);
                    return;
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
