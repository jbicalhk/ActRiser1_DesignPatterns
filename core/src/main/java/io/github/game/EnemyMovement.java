package io.github.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyMovement implements MovementStrategy {
    private Rectangle rect;

    public EnemyMovement(Rectangle rect) {
        this.rect = rect;
    }
    
    @Override
    public void move(float deltaTime, Player player) {
        Vector2 playerPosition = new Vector2(player.rect.x, player.rect.y);
        Vector2 enemyPosition = new Vector2(rect.getX(), rect.getY());

        // Follow player if within range
        if (enemyPosition.dst(playerPosition) < 100) {
            Vector2 direction = playerPosition.sub(enemyPosition).nor();
            rect.x += direction.x * 100 * deltaTime;
            rect.y += direction.y * 100 * deltaTime;
        }

    }
}