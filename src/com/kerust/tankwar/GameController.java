package com.kerust.tankwar;

public class GameController {

    private static boolean isEnableSound = true;

    public static boolean enableSound() {
        return isEnableSound;
    }

    public static void enableSound(boolean enableSound) {
        isEnableSound = enableSound;
    }

    public static void main(String[] args) {
         new TankWarFrame().loop();
    }
}
