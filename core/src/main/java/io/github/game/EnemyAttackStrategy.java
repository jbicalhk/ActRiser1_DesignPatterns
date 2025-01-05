package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

// EnemyAttackStrategy atualizada
public class EnemyAttackStrategy implements AttackStrategy {
    private Rectangle enemyBounds;
    private float attackCooldown;

    public EnemyAttackStrategy(Rectangle bounds) {
        this.enemyBounds = bounds;
        this.attackCooldown = 0;
    }

    @Override
    public void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        if (attackCooldown <= 0) {
            // Verificar colisão com o bounds do player
            if (enemyBounds.overlaps(player.getBounds())) {
                attackCooldown = 2f; // Cooldown de 2 segundos
                player.setVida(player.getVida() - 5);
                System.out.println("Enemy attacks player! Player vida: " + player.getVida());
            }
        }
        attackCooldown -= deltaTime;
    }
}