package com.obstacleavoid.Entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.obstacleavoid.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable{




    private float ySpeed = GameConfig.EASY_OBSTACLE_SPEED;
    private boolean hit;


    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE,GameConfig.OBSTACLE_SIZE);

    }


    public void update() {
        setY(getY() - ySpeed);

        updateBounds();
    }




    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());

        hit = overlaps;

        return overlaps;
    }

    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float YSpeed) {
        this.ySpeed = YSpeed;
    }

    @Override
    public void reset() {
        hit = false;
    }
}
