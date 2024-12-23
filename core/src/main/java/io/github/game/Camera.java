package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera extends OrthographicCamera {
	private OrthographicCamera camera;
	private Viewport viewport;

	public Camera() {
		// TODO Auto-generated constructor stub
	}

	public void createACamera(Player player) {
		camera = new OrthographicCamera();
		viewport = new FitViewport(100 / 32f, 100 / 32f, camera);
	}
	
	public void render(Player player) {
		camera.position.set(player.getX() / 2, player.getY() / 2, 0);

	}

}
