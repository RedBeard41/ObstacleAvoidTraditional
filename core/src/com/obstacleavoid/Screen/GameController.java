package com.obstacleavoid.Screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.Entity.Obstacle;
import com.obstacleavoid.Entity.Player;
import com.obstacleavoid.config.DifficultyLevel;
import com.obstacleavoid.config.GameConfig;

public class GameController {

    //== Constants ==

    private static final Logger log = new Logger(GameController.class.getSimpleName(), Logger.DEBUG);

    //instance variable
    private Player player;
    //private boolean isAlive = true;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacleTimer = 0f;
    private float scoreTimer = 0f;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.HARD;
    private int lives = GameConfig.LIVES_START;


    // constructor
    public GameController() {
        init();
    }

    //init
    private void init() {
        //create player
        player = new Player();

        //calculate position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2;
        float startPlayerY = 1;


        //position player
        player.setPosition(startPlayerX, startPlayerY);

    }

    //public methods
    public void update(float delta) {

        if (isGameOver()) {
            log.debug("Game Over!");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);
        blockPlayerFromLeavingTheWorld();
        if (isPlayerCollidingWithObstacle()) {
            log.debug("Collision detected");
            lives--;
        }

    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    //private methods
    private boolean isGameOver() {

       // return lives <= 0;
        return false;
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score, displayScore + (int) (60 * delta));
        }

    }

    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {

                return true;
            }
        }

        return false;
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(player.getX(), player.getWidth() / 2f,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2f);

        /*if(playerX < player.getWidth()/2f){
            playerX = player.getWidth()/2f;
        }
        else if(playerX> GameConfig.WORLD_WIDTH - player.getWidth()/2f){
            playerX = GameConfig.WORLD_WIDTH - player.getWidth()/2f;
        }*/

        player.setPosition(playerX, player.getY());
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void removePassedObstacles(){
        if(obstacles.size>0){
            Obstacle first = obstacles.first();

            float minObstacleY = -Obstacle.SIZE;

            if(first.getY() < minObstacleY){
                obstacles.removeValue(first,true);
            }
        }
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0f+Obstacle.SIZE/2f;
            float max = GameConfig.WORLD_WIDTH-Obstacle.SIZE/2f;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void updatePlayer() {
        //log.debug("playerX: "+ player.getX() + " playerY: "+ player.getY());
        player.update();

    }
}
