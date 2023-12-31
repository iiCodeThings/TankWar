package com.kerust.tankwar;

import com.kerust.tankwar.award.*;
import com.kerust.tankwar.weapon.Weapon;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankWarFrame extends Frame {

    /* 游戏刷新时间：30FPS */
    public static final int BASE = 30;
    public static final int EXIT_SUCCESS = 0;
    public static int LAYOUT_WIDTH = 1920;
    public static int LAYOUT_HEIGHT = 1080;
    public static int MAX_HOSTILE_TANK_NUM = 8;
    public static int HOSTILE_TANK_ADD_STEP = 1;
    public static int INIT_HOSTILE_TANK_NUM = 5;
    public static final String TANK_WAR_TITLE = "Tank War";

    private Random random = new Random();

    /* 无敌模式，默认关闭 */
    private boolean isGodOn = true;

    /* 游戏是否暂停（按下了暂停键）*/
    private boolean isPause = false;

    private Image offsetScreen = null;

    /* 爆炸效果 */
    private List<Explode> explodes = new ArrayList<>();

    /* 敌对坦克*/
    private List<Tank> hostileTanks = new ArrayList<>();

    /* 主战坦克的子弹*/
    private List<Weapon> weapons = new ArrayList<>();

    /* 主战坦克*/
    private Tank mainTank = new Tank(0, 100, Group.GOOD, this);

    /* 奖励 */
    private List<Award> awards = new ArrayList<>();

    private static boolean isEnableSound = true;
    private boolean isHardMode = false;

    public static boolean enableSound() {
        return isEnableSound;
    }
    public static void enableSound(boolean enableSound) {
        isEnableSound = enableSound;
    }

    static {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        LAYOUT_WIDTH = (int)dimension.getWidth();
        LAYOUT_HEIGHT = (int)dimension.getHeight();
    }

    public TankWarFrame() {
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle(TANK_WAR_TITLE);

        this.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(EXIT_SUCCESS);
            }
        });
        this.setMenuBar(new TankFrameMenuBar(mainTank, this).createMenuBar());
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

    public boolean getGodOn() { return this.isGodOn; }

    public void setGodOn(boolean on) { this.isGodOn = on; }

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

    public boolean hardMode() { return  this.isHardMode; }
    public void hardMode(boolean hardMode) { this.isHardMode = hardMode; }

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

    public void pause() {
        isPause = ! isPause;
        Sound.play_award_sound();
    }

    public void loop() {

        while(true) {

            if (! isPause) {
                this.repaint();
            }

            try {
                Thread.sleep(1000 / BASE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Graphics g) {
        if (offsetScreen == null) {
            offsetScreen = createImage(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        }
        Graphics offsetGraphics = offsetScreen.getGraphics();
        offsetGraphics.setColor(Color.BLACK);
        offsetGraphics.fillRect(0, 0, LAYOUT_WIDTH, LAYOUT_HEIGHT);
        paint(offsetGraphics);
        g.drawImage(offsetScreen, 0, 0, null);
    }

    public Award getAwardByTank(Tank tank) {

        Award award = null;

        switch (tank.getAwardType()) {
            case STAR:
                award = new StarAward(tank.getX(), tank.getY(), this);
                break;
            case MINE:
                award = new MineAward(tank.getX(), tank.getY(), this);
                break;
            case DINOSAUR:
                award = new DinosaurAward(tank.getX(), tank.getY(), this);
                break;
            case TANK:
                award = new TankAward(tank.getX(), tank.getY(), this);
                break;
            default:
                break;
        }

        return award;
    }

    @Override
    public void paint(Graphics g) {
        
        showInfo(g);

        if (mainTank.getLiving()) {
            mainTank.paint(g);
        }

        if (isHardMode) {
            this.addHostileTanks();
        }

        for (int i = 0; i < weapons.size(); i ++) {
            weapons.get(i).paint(g);
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
            for (int j = 0; j < weapons.size(); j ++) {
                Weapon weapon = weapons.get(j);
                if (tank.isCollideWith(weapon)) {
                    tank.die();
                    weapon.die();
                    addExplode(new Explode(tank.getX(), tank.getY(), this));
                    Award award = this.getAwardByTank(tank);
                    if (award != null) {
                        addAward(award);
                    }
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
                    mainTank.setWeaponType(Weapon.Type.SUPER_MISSILE);
                } else if (award.getType() == Award.Type.TANK) {
                    /* 成功吃掉会增加一条命 */
                    mainTank.addLifeNumber();
                } else if (award.getType() == Award.Type.DINOSAUR) {
                    mainTank.setWeaponType(Weapon.Type.DINOSAUR);
                }
            }
        }

        /* 是否被敌方子弹击中 */
        for (int i = 0; i < weapons.size(); i ++) {
            Weapon weapon = weapons.get(i);
            if (mainTank.getLiving() && mainTank.isCollideWith(weapon)) {
                weapon.die();
                mainTank.subLifeNumber();
                if (mainTank.getLifeNumber() == 0 && ! isGodOn) {
                    mainTank.die();
                    Sound.play_game_over_sound();
                }
                addExplode(new Explode(mainTank.getX(), mainTank.getY(), this));
            }
        }
    }

    private void showInfo(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        graphics.drawString("Bullet Num: " + weapons.size(),15, 45);
        graphics.drawString("Tank  Num: " + hostileTanks.size(),15, 60);
        graphics.drawString("MainTank Life: " + mainTank.getLifeNumber(),15, 75);
        graphics.drawString("Killed Tank Num: " + mainTank.getKilledTankNumber(),15, 90);
        graphics.setColor(color);
    }

    public void addBullet(Weapon weapon) {
        weapons.add(weapon);
    }

    public void removeBullet(Weapon weapon) {
        weapons.remove(weapon);
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
                    pause();
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
