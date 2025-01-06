package io.github.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class EnemyAttackStrategy implements AttackStrategy {
    private Rectangle enemyBounds;
    private float attackCooldown;
    private float interceptDelay;
    private boolean playerIntercepted;
    
    public EnemyAttackStrategy(Rectangle bounds) {
        this.enemyBounds = bounds;
        this.attackCooldown = 0;
        this.interceptDelay = 1f;
    }

    @Override
    public void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        if (enemyBounds.overlaps(player.getBounds())) {
            if (!playerIntercepted) {
                playerIntercepted = true;
                interceptDelay = 1f;
            }
            if (interceptDelay > 0) {
                interceptDelay -= deltaTime;
                return; 
            }
            if (attackCooldown <= 0) {
                attackCooldown = 2f; 
                player.setVida(player.getVida() - 5);
                System.out.println("Enemy attacks player! Player vida: " + player.getVida());
            }
        } else {
            playerIntercepted = false;
            interceptDelay = 1f;
        }

        attackCooldown -= deltaTime;
    }
}