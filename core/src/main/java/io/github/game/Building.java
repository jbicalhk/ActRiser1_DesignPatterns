/*package io.github.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Building {
    private TiledMap tiledMap;
    private MapLayer buildingsLayer;

    public Building(TiledMap map, String layerName) {
        this.tiledMap = map;
        this.buildingsLayer = tiledMap.getLayers().get(layerName);
        if (this.buildingsLayer == null) {
            throw new IllegalArgumentException("Layer '" + layerName + "' not found in the map.");
        }
    }

    public void addBuilding(String name, float x, float y) {
        MapObject building = new MapObject();
        building.setName(name);
        building.getProperties().put("x", x);
        building.getProperties().put("y", y);
        buildingsLayer.getObjects().add(building);
    }

    public void render(SpriteBatch batch, Texture buildingTexture) {
        for (MapObject object : buildingsLayer.getObjects()) {
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");
            batch.begin();
            batch.draw(buildingTexture, x, y);
            batch.end();
        }
    }
}
*/