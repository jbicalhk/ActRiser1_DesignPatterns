package io.github.game;

import com.badlogic.gdx.utils.Array;

public interface AttackStrategy {
	void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles);
}
