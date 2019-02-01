package com.obstacleavoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.Screen.GameScreen;

public class ObstacleAvoidGame extends Game {

    private static final Logger log = new Logger(ObstacleAvoidGame.class.getSimpleName(), Logger.DEBUG);

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new GameScreen());
    }

}
