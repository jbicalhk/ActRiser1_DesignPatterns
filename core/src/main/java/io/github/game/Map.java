package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TmxMapLoader mapLoader;

	public Map(String mapFilePath) {
		mapLoader = new TmxMapLoader();
		tiledMap = mapLoader.load(mapFilePath);
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		updateLayerVisibility(5);
	}

	public void render(OrthographicCamera camera) {
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	/**
	 * @param visibleLayersCount 
	 */
	public void updateLayerVisibility(int visibleLayersCount) {
		for (int i = 0; i < tiledMap.getLayers().getCount(); i++) {
			MapLayer layer = tiledMap.getLayers().get(i);
			if (i < visibleLayersCount) {
				layer.setVisible(true);
			} else {
				layer.setVisible(false);
			}
		}
	}

	/**
	 * @param index
	 */
	public void activateLayer(int index) {
		if (index >= 5 && index < tiledMap.getLayers().getCount()) {
			MapLayer layer = tiledMap.getLayers().get(index);
			if (layer != null) {
				layer.setVisible(true);
				System.out.println(layer.toString());
			}
		}
	}

	public void dispose() {
		tiledMap.dispose();
		mapRenderer.dispose();
	}
}
