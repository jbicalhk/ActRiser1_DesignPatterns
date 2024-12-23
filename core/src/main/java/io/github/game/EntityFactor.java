package io.github.game;

class EntityFactory {
    public static Entity createEntity(String type) {
        switch (type) {
            case "Player":
                return new Player();
            case "Enemy":
                return new Enemy();
            default:
                throw new IllegalArgumentException("Tipo de entidade desconhecido: " + type);
        }
    }

    public static Building createBuilding() {
        return new Building();
    }
}