package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityFactor {
    public static Entity createEntity(String type, float posX, float posY, SpriteBatch batch) {
        if (batch == null) {
            throw new IllegalArgumentException("SpriteBatch cannot be null");
        }
        
        switch (type) {
            case "Player":
                return new Player(posX, posY, batch);
            case "Enemy":
                return new Enemy(posX, posY, batch);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }

    public static Building createBuilding() {
        return new Building();
    }
}