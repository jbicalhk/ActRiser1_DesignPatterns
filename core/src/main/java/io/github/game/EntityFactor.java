package io.github.game;

class EntityFactory {
    public static Entity createEntity(String type, float posX, float posY) {
        switch (type) {
            case "Player":
            	return new Player(posX, posY);
            case "Enemy":
                return new Enemy(posX, posY);
            default:
                throw new IllegalArgumentException("Tipo de entidade desconhecido: " + type);
        }
    }

    public static Building createBuilding() {
        return new Building();
    }
}