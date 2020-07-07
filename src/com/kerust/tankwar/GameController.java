package com.kerust.tankwar;

import java.awt.*;

public class GameController {

    public static final int BASE = 30;

    public static void main(String[] args) {
        TankWarFrame tankWarFrame = new TankWarFrame();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(dimension.getWidth() + " " +  dimension.getHeight());
        while (true) {
            tankWarFrame.repaint();
            try {
                Thread.sleep(1000 / BASE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
