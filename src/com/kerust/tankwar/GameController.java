package com.kerust.tankwar;

import java.awt.*;

public class GameController {

    public static final int BASE = 30;

    /* 无敌模式，默认fasle */
    public static final boolean GOD_ON = false;
    public static final boolean ENABLE_SOUND = true;

    private static boolean isGameOver = false;

    public static void main(String[] args) {

        TankWarFrame tankWarFrame = new TankWarFrame();

        while (! isGameOver) {
            tankWarFrame.repaint();
            try {
                Thread.sleep(1000 / BASE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* 继续重绘约2s */
        for (int i = 0; i < 66; i ++) {
            tankWarFrame.repaint();
            try {
                Thread.sleep(1000 / BASE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Sound.play_game_over_sound();
        tankWarFrame.gameOver();
    }

    public static void gameOver() {
        if (GOD_ON) {
            return;
        }
        isGameOver = true;
    }
}
