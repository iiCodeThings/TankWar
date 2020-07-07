package com.kerust.tankwar;

public class Sound {

    public static final String AWARD_SOUND_PATH = "images/audio/award.wav";
    public static final String EXPLODE_SOUND_PATH = "images/audio/boom.wav";
    public static final String GOOD_BULLET_SOUND_PATH = "images/audio/good_bullet.wav";
    public static final String BAD_BULLET_SOUND_PATH = "images/audio/bad_bullet.wav";
    public static final String GAME_OVER_SOUND = "images/audio/game_over.wav";

    public static void play_award_sound() {
        if (GameController.ENABLE_SOUND) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(AWARD_SOUND_PATH)).start();
        }
    }

    public static void play_explode_sound() {
        if (GameController.ENABLE_SOUND) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(EXPLODE_SOUND_PATH)).start();
        }
    }

    public static void play_good_bullet_sound() {
        if (GameController.ENABLE_SOUND) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(GOOD_BULLET_SOUND_PATH)).start();
        }
    }

    public static void play_bad_bullet_sound() {
        if (GameController.ENABLE_SOUND) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(BAD_BULLET_SOUND_PATH)).start();
        }
    }

    public static void play_game_over_sound() {
        if (GameController.ENABLE_SOUND) {
            new Audio(ResourceMgr.class.getClassLoader().getResourceAsStream(GAME_OVER_SOUND)).start();
        }
    }
}
