package com.obstacleavoid.Screen.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.config.GameConfig;

public class GameScreen implements Screen {
    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private GameController gameController;
    private GameRenderer renderer;
    private GameInput gameInput;
    private Viewport gameViewPort;


    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        log.debug("show");

        //moving to loading screen
       /* waitMillis(800);
        assetManager.load(AssetDescriptors.FONT);
        waitMillis(800);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        waitMillis(800);
       *//* assetManager.load(AssetDescriptors.BACKGROUND);
        assetManager.load(AssetDescriptors.PLAYER);
        assetManager.load(AssetDescriptors.OBSTACLE);*//*

        assetManager.finishLoading();*/

        gameViewPort = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        gameInput = new GameInput(gameViewPort);
        gameController = new GameController(gameInput);
        renderer = new GameRenderer(gameController, gameViewPort, assetManager);

        Gdx.input.setInputProcessor(gameInput);


    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        renderer.render(delta);

    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("hide");

    }

    @Override
    public void dispose() {
        renderer.dispose();

    }


}
