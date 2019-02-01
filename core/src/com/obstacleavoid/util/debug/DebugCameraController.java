package com.obstacleavoid.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {

    private static final Logger log = new Logger(DebugCameraController.class.getSimpleName(), Logger.DEBUG);

    //==Constants ==


    // == attributes ==
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    private DebugCameraConfig debugCameraConfig;

    //constructor
    public DebugCameraController() {
        debugCameraConfig = new DebugCameraConfig();
        log.info("camera config: "+ debugCameraConfig);
    }

    //== public methods
    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = debugCameraConfig.getMoveSpeed() * delta;
        float zoomSpeed = debugCameraConfig.getZoomSpeed() * delta;

        //move controls
        if (debugCameraConfig.isLeftPressed()) {
            moveLeft(moveSpeed);
        }

        if (debugCameraConfig.isRightPressed()) {
            moveRight(moveSpeed);
        }

        if (debugCameraConfig.isUpPressed()) {
            moveUp(moveSpeed);
        }

        if (debugCameraConfig.isDownPressed()) {
            moveDown(moveSpeed);
        }

        //zoom controls

        if (debugCameraConfig.isZoomInPressed()) {
            zoomIn(zoomSpeed);
        }

        if (debugCameraConfig.isZoomOutPressed()) {
            zoomOut(zoomSpeed);
        }

        //reset controls
        if(debugCameraConfig.isResetPressed()){
            reset();
        }

        if(debugCameraConfig.isLogPressed()){
            logDebug();
        }
    }

    //private methods
    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);

    }

    private void setZoom(float value){
        zoom = MathUtils.clamp(value,debugCameraConfig.getMaxZoomIn(), debugCameraConfig.getMaxZoomOut());
    }

    private void zoomIn(float zoomSpeed){
        setZoom(zoom+zoomSpeed);
    }

    private void zoomOut(float zoomSpeed){
        setZoom(zoom-zoomSpeed);
    }

    private void reset(){
        position.set(startPosition);
        setZoom(1f);
    }

    private void logDebug(){
        log.debug("position: "+ position + " zoom: "+ zoom);
    }



    private void moveLeft(float speed) {
        moveCamera(-speed, 0);
    }

    private void moveRight(float speed) {
        moveCamera(speed, 0);

    }

    private void moveDown(float speed) {
        moveCamera(0, speed);

    }

    private void moveUp(float speed) {
        moveCamera(0, -speed);

    }




}
