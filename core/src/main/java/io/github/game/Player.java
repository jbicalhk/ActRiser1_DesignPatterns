package io.github.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class Player extends Entity {
	Rectangle rect;
	float projectileCooldown = 0;
	private int kills = 0;
	private List<Projectile> bullets;
	private MovementStrategy movementStrategy;
	private AttackStrategy attackStrategy;
	
	Player(float posX, float posY) {
        super(posX, posY);
        this.rect = new Rectangle(posX, posY, 16, 16);
        this.vida = 100;
        this.dano = 10;
        this.movementStrategy = new WalkMovement(rect);
        this.attackStrategy = new PlayerAttackStrategy(rect);
    }

	public void addKill() {
		kills++;
		System.out.println("Inimigos derrotados: " + kills);
	}

	public int getKills() {
		return kills;
	}
	public float getX() {
		return rect.x;
	}

    public float getY() {
		return rect.y;
	}
    
    
	
	@Override
	void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        movementStrategy.move(deltaTime, player);
        attackStrategy.attack(deltaTime, this, enemies, projectiles);
    }
	@Override
	void render(ShapeRenderer renderer) {
		renderer.setColor(Color.BLUE);
		renderer.rect(rect.x, rect.y, rect.width, rect.height);
	}
	public List<Projectile> getBullets() {
        return bullets;
    }
}
