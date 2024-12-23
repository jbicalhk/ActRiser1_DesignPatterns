package io.github.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class EnemyAttackStrategy implements AttackStrategy {
    private Rectangle rect;
    private float attackCooldown;

    public EnemyAttackStrategy(Rectangle rect) {
        this.rect = rect;
        this.attackCooldown = 0;
    }

    //combinar isso daqui com a função de morrer do player
    @Override
    public void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        if (rect.overlaps(player.rect) && attackCooldown <= 0) {
            attackCooldown = 2f; // Cooldown de 2 segundos
            player.setVida(player.getVida() - 5); // Reduz 5 de vida do jogador CRIAR UMA FUNÇÃO REDUCE NA CLASSE PLAEYR
            System.out.println("Enemy attacks player! Player vida: " + player.getVida());
        }
        attackCooldown -= deltaTime;
    }
}

