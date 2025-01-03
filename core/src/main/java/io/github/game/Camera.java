package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
    private static OrthographicCamera camera;
    
    
    public void createCamera() {
        camera = new OrthographicCamera();      
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
 //NUNCA TIRAR ISSO DAQUI
    }

    public void updateCamera(Player player) {
    	 float lerp = 0.13f; // Taxa de interpolação (quanto menor, mais suave)
    	    camera.position.x += (player.getX() + player.rect.width / 2  - camera.position.x) * lerp;
    	    camera.position.y += (player.getY() + player.rect.height / 2 - camera.position.y) * lerp;
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
