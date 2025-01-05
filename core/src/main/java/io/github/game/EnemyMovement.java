package io.github.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyMovement implements MovementStrategy {
    private Rectangle bounds;

    public EnemyMovement(Rectangle bounds) {
        this.bounds = bounds;
    }
    
    @Override
    public void move(float deltaTime, Player player) {
        Vector2 playerPosition = new Vector2(player.getPosX(), player.getPosY());
        Vector2 enemyPosition = new Vector2(bounds.getX(), bounds.getY());

        // Follow player if within range
        if (enemyPosition.dst(playerPosition) < 100) {
            Vector2 direction = playerPosition.sub(enemyPosition).nor();
            bounds.x += direction.x * 100 * deltaTime;
            bounds.y += direction.y * 100 * deltaTime;
        }

    }
}