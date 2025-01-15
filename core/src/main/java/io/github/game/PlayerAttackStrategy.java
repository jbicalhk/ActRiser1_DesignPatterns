package io.github.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class PlayerAttackStrategy implements AttackStrategy {
	private Rectangle playerBounds;
	private float attackCooldown;
	private float projectileCooldown;
	private Sound attack;
	private Sound punch;

	public PlayerAttackStrategy(Rectangle bounds) {
		this.playerBounds = bounds;
		this.attackCooldown = 0;
		this.projectileCooldown = 0;
		this.attack = Gdx.audio.newSound(Gdx.files.internal("sounds/projectile.wav"));
		this.punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.wav"));
	}

	@Override
	public void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
		// Ataque corpo a corpo
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && attackCooldown <= 0) {

			for (Enemy enemy : enemies) {
				if (playerBounds.overlaps(enemy.getBounds())) {
					punch.play((float) 0.15);
					enemy.takeDamage(15);
				}
			}
			attackCooldown = 1.2f;
		}

		// Ataque à distância (projétil)
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && projectileCooldown <= 0) {

			attack.play((float) 0.1);
			Vector2 mouseWorldPos = Camera.getWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());

			Vector2 direction = new Vector2(mouseWorldPos.x - (playerBounds.x + playerBounds.width / 2),
					mouseWorldPos.y - (playerBounds.y + playerBounds.height / 2)).nor();

			projectiles.add(new Bullet(playerBounds.x + playerBounds.width / 2,
					playerBounds.y + playerBounds.height / 2, direction, 50, 500, 2f));
			projectileCooldown = 2f;
		}

		attackCooldown -= deltaTime;
		projectileCooldown -= deltaTime;
	}
}