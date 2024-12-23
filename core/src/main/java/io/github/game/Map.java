package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    
   // private TiledMap tiledMap;
   // private OrthogonalTiledMapRenderer mapRenderer;
   // TmxMapLoader mapLoader = new TmxMapLoader();
	private Texture backgroundTexture;
    private SpriteBatch spriteBatch;
     
    public Map() {
        backgroundTexture = new Texture("35573.jpg");
        spriteBatch = new SpriteBatch();
    }
    
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
    }
    
	public void createMap() {
		//tiledMap = mapLoader.load("mapa.tmx");
		
	}
	
	public void dispose() {
        backgroundTexture.dispose();
        spriteBatch.dispose();
    }
     
}
