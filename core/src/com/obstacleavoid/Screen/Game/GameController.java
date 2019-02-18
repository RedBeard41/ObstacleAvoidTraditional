package com.obstacleavoid.Screen.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.obstacleavoid.Entity.Background;
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
    private Background background;

    private float obstacleTimer = 0f;
    private float scoreTimer = 0f;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    private int lives = GameConfig.LIVES_START;
    private Pool<Obstacle> obstaclePool;
    private final float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
    private final float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;

    private GameInput gameInput;


    // constructor
    public GameController(GameInput gameInput) {
        this.gameInput = gameInput;
        init();
    }

    //init
    private void init() {


        //create player
        player = new Player();

        //position player
        player.setPosition(startPlayerX, startPlayerY);

        //create obstacle pool
        obstaclePool = Pools.get(Obstacle.class, 15);

        //create background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

    }

    //public methods
    public void update(float delta) {

        if (isGameOver()) {

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

            if (isGameOver()) {
                log.debug("Game Over");
            } else {
                restart();
            }
        }

    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    //private methods

    private void restart() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        player.setPosition(startPlayerX, startPlayerY);

    }

    private boolean isGameOver() {

        return lives <= 0;

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
        float playerX = MathUtils.clamp(player.getX(),
                0,
                GameConfig.WORLD_WIDTH - player.getWidth());

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

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            Obstacle first = obstacles.first();

            float minObstacleY = -GameConfig.OBSTACLE_SIZE;

            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;
            float obstacleX = MathUtils.random(min, max);

            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void updatePlayer() {
        //log.debug("playerX: "+ player.getX() + " playerY: "+ player.getY());

        float xSpeed = 0;


        if (gameInput.isRight()) {
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        }

        if (gameInput.isLeft()) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        if (Gdx.input.isTouched()) {
            float xTouch = gameInput.getTouch().x;
            xSpeed = xTouch < player.getX() ? -GameConfig.MAX_PLAYER_X_SPEED : GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setX(player.getX() + xSpeed);


    }
}
