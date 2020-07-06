package com.kerust.tankwar;

public class GameController {

    public static void main(String[] args) {
        TankWarFrame tankWarFrame = new TankWarFrame();

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
