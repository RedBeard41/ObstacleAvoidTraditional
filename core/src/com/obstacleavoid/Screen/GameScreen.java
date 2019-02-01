package com.obstacleavoid.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.Entity.Player;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;

public class GameScreen implements Screen{

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private Player player;
    private DebugCameraController debugCameraController;


    public GameScreen() {
    }

    @Override
    public void show() {
       camera = new OrthographicCamera();
       viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
       renderer = new ShapeRenderer();

       //create player
       player = new Player();

       //calculate position
        float startPlayerX = GameConfig.WORLD_WIDTH/2;
        float startPlayerY = 1;


        //position player
        player.setPosition(startPlayerX,startPlayerY);

        //create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

    }


    @Override
    public void render(float delta) {

        //debug camera controller input
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        //update world
        update(delta);

        //clear screen
        GdxUtils.clearScreen();

        //render debug graphics
        renderDebug();




    }

    private void update(float delta){
        updatePlayer();
    }

    private void updatePlayer(){
        //log.debug("playerX: "+ player.getX() + " playerY: "+ player.getY());
        player.update();

    }

    private void renderDebug(){
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        renderer.end();

        ViewportUtils.drawGrid(viewport,renderer);
    }

    private void drawDebug(){
        player.drawDebug(renderer);

    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }
}
