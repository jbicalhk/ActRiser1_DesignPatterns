package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
    private OrthographicCamera camera;
    
    
    public void createCamera() {
        camera = new OrthographicCamera();      
        camera.setToOrtho(false, 250, 250); //NUNCA TIRAR ISSO DAQUI
    }

    public void updateCamera(Player player) {
        camera.position.set(player.getX() , player.getY() , 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
