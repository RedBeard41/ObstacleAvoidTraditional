package com.obstacleavoid.Screen.Game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.config.GameConfig;

public class GameInput implements InputProcessor {
    private Viewport viewport;
    private boolean left, right, keyProcessed;
    private int inputKey;
    private Vector2 touch;

    public GameInput(Viewport viewport) {
        this.viewport = viewport;
        touch = new Vector2(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.RIGHT) {
            right = true;
            return true;
        }

        if (keycode == Input.Keys.LEFT) {
            left = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT) {
            right = false;
            return true;
        }

        if (keycode == Input.Keys.LEFT) {
            left = false;
            return true;
        }

        return false;
    }



    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,screenY);
        viewport.unproject(touch);

        return false;
    }

    public Vector2 getTouch(){
        return touch;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}
