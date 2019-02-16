package com.obstacleavoid.config;

public class GameConfig {


    public static final float WIDTH = 480f; //pixels
    public static final float HEIGHT = 800f; //pixels

    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;

    public static final float WORLD_WIDTH = 6f; //world units
    public static final float WORLD_HEIGHT = 10f; //world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2; //world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2; //world units

    public static final float OBSTACLE_SPAWN_TIME = 0.25f;

    public static final float MAX_PLAYER_X_SPEED = 0.25f;  //world units

    public static final int LIVES_START = 3;
    public static final float SCORE_MAX_TIME = 1.25f;

    public static final float EASY_OBSTACLE_SPEED = 0.07f;
    public static final float MEDIUM_OBSTACLE_SPEED = 0.15f;
    public static final float HARD_OBSTACLE_SPEED = 0.18f;

    public static final float PLAYER_BOUNDS_RADIUS = 0.4f; //world units
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS;

    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f; //world units
    public static final float OBSTACLE_SIZE = 2 * OBSTACLE_BOUNDS_RADIUS;

    private GameConfig() {
    }
}
