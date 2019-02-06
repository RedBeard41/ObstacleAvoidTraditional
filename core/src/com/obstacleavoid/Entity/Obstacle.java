package com.obstacleavoid.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.obstacleavoid.config.DifficultyLevel;
import com.obstacleavoid.config.GameConfig;

public class Obstacle extends GameObjectBase {

    private static final float BOUNDS_RADIUS = 0.4f; //world units
    public static final float SIZE = 2 * BOUNDS_RADIUS;


    private float ySpeed = GameConfig.EASY_OBSTACLE_SPEED;
    private boolean hit;


    public Obstacle() {
        super(BOUNDS_RADIUS);

    }


    public void update() {
        setY(getY() - ySpeed);

        updateBounds();
    }



    public float getWidth() {
        return SIZE;
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds,getBounds());

        hit = overlaps;

        return overlaps;
    }

    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float YSpeed) {
        this.ySpeed = YSpeed;
    }
}
