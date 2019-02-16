package com.obstacleavoid.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.config.GameConfig;

public class GameScreen implements Screen {

    private GameController gameController;
    private GameRenderer renderer;
    private GameInput gameInput;
    private Viewport gameViewPort;


    @Override
    public void show() {
        gameViewPort = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        gameInput = new GameInput(gameViewPort);
        gameController = new GameController(gameInput);
        renderer = new GameRenderer(gameController,gameViewPort);

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

    }

    @Override
    public void dispose() {
        renderer.dispose();

    }
}
