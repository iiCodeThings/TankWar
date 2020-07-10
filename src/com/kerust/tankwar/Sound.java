package com.kerust.tankwar;

public class Sound {

    public static final String AWARD_SOUND_PATH = "images/audio/award.wav";
    public static final String EXPLODE_SOUND_PATH = "images/audio/boom.wav";
    public static final String GAME_OVER_SOUND = "images/audio/game_over.wav";
    public static final String BULLET_SOUND_PATH = "images/audio/bad_bullet.wav";
    public static final String MISSILE_SOUND_PATH = "images/audio/good_bullet.wav";

    public static void play_award_sound() {
        if (GameController.enableSound()) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(AWARD_SOUND_PATH)).start();
        }
    }

    public static void play_explode_sound() {
        if (GameController.enableSound()) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(EXPLODE_SOUND_PATH)).start();
        }
    }

    public static void play_missile_sound() {
        if (GameController.enableSound()) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(MISSILE_SOUND_PATH)).start();
        }
    }

    public static void play_bullet_sound() {
        if (GameController.enableSound()) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(BULLET_SOUND_PATH)).start();
        }
    }

    public static void play_game_over_sound() {
        if (GameController.enableSound()) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(GAME_OVER_SOUND)).start();
        }
    }
}
