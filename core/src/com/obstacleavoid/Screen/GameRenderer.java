package com.obstacleavoid.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.Entity.Background;
import com.obstacleavoid.Entity.Obstacle;
import com.obstacleavoid.Entity.Player;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.AssetPaths;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    //attributes

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;
    private final GameController gameController;
    private GameInput gameInput;

    private final AssetManager assetManager;
    private Texture playerTexture;
    private Texture obstacleTexture;
    private Texture backgroundTexture;

    public GameRenderer(GameController gameController, Viewport viewport, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.viewport = viewport;
        this.gameController = gameController;

        init();
    }

    private void init(){
        camera = new OrthographicCamera();
        viewport.setCamera(camera);

        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = assetManager.get(AssetDescriptors.FONT);

        //create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        playerTexture = assetManager.get(AssetDescriptors.PLAYER);
        obstacleTexture = assetManager.get(AssetDescriptors.OBSTACLE);
        backgroundTexture = assetManager.get(AssetDescriptors.BACKGROUND);

    }

    //public methods
    public void render(float delta){
        //debug camera controller input
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);




        //clear screen
        GdxUtils.clearScreen();

        //render gameplay
        renderGamePlay();

        //render UI
        renderUI();

        //render debug graphics
        renderDebug();
    }




    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();

    }



    //private methods


    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //draw Background
        Background background = gameController.getBackground();
        batch.draw(backgroundTexture,
                background.getX(),
                background.getY(),
                background.getWidth(),
                background.getHeight());

        //draw player
        Player player = gameController.getPlayer();
        batch.draw(playerTexture,
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight());

        for(Obstacle obstacle: gameController.getObstacles()){
            batch.draw(obstacleTexture,
                    obstacle.getX(),
                    obstacle.getY(),
                    obstacle.getWidth(),
                    obstacle.getHeight());
        }

        batch.end();
    }

    private void renderUI() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + gameController.getLives();
        layout.setText(font, livesText);
        font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = "SCORE: " + gameController.getDisplayScore();
        layout.setText(font, scoreText);
        font.draw(batch, scoreText, GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height);

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        Player player = gameController.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = gameController.getObstacles();
        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }

    }


}
