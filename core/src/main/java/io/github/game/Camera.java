package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera {
    private static OrthographicCamera camera;
    
    public void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    public void updateCamera(Player player) {
        float lerp = 0.1f; // Suavização do movimento da câmera
        float targetX = player.getPosX() + player.getBounds().width / 2;
        float targetY = player.getPosY() + player.getBounds().height / 2;
        
        camera.position.x += (targetX - camera.position.x) * lerp;
        camera.position.y += (targetY - camera.position.y) * lerp;
        camera.update();
    }

    public static Vector2 getWorldCoordinates(float screenX, float screenY) {
        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        return new Vector2(worldCoords.x, worldCoords.y);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
    
    
    
}