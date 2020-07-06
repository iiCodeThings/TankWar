package com.kerust.tankwar;

import java.awt.*;

public class GameController {

    public static void main(String[] args) {
        TankWarFrame tankWarFrame = new TankWarFrame();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(dimension.getWidth() + " " +  dimension.getHeight());
        while (true) {
            tankWarFrame.repaint();
            try {
                Thread.sleep(1000 / 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
