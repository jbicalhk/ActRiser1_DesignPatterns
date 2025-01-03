package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
       
    }

    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
    
}
