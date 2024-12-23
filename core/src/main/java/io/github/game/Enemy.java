package io.github.game;
import java.util.Random;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class Enemy extends Entity{
    Rectangle rect;
    float health = 100;
    Random random = new Random();
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;

    Enemy() {
        this.rect = new Rectangle(random.nextInt(440), random.nextInt(440), 40, 40);
        this.vida = 50;
        this.dano = 5;
        this.movementStrategy = new EnemyMovement(rect);
        this.attackStrategy = new EnemyAttackStrategy(rect);
    }

    @Override
    void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        if (health <= 0) return;

        ((EnemyMovement) movementStrategy).move(deltaTime, player);
        attackStrategy.attack(deltaTime, player, null, null);
    }
    void takeDamage(float damage) {
        health -= damage;
    }

    boolean isDead() {
        return health <= 0;
        
    }
    

    

	@Override
	void render(ShapeRenderer renderer) {
		renderer.setColor(health > 0 ? Color.RED : Color.DARK_GRAY);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
		
	}
}