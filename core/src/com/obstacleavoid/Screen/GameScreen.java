package com.obstacleavoid.Screen;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private GameController gameController;
    private GameRenderer renderer;


    @Override
    public void show() {
        gameController = new GameController();
        renderer = new GameRenderer(gameController);

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
