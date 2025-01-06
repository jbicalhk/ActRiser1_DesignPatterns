package io.github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullet extends Projectile {

	private float speed;

	Bullet(float x, float y, Vector2 direction, int dano, float speed, float lifetime) {
		super(x, y, direction, dano);
		this.speed = speed;
		this.lifetime = lifetime;
	}

	@Override
	public void update(float deltaTime, Array<Enemy> enemies) {

		position.x += direction.x * speed * deltaTime;
		position.y += direction.y * speed * deltaTime;
		lifetime -= deltaTime;

		// Check collision with enemies
		for (Enemy enemy : enemies) {
			if (enemy.getBounds().contains(position.x, position.y)) {
				enemy.takeDamage(50);// TANTO DE DAMAGE QUE A BALA VAI DAR
				lifetime = 0; // Expire projectile
				break;
			}
		}
	}

	// ISSO DAQUI É QUAL A FORMA DA BALA VAI ASSUMIR
	public void render(Camera camera, ShapeRenderer renderer) {
		renderer.setProjectionMatrix(camera.getCamera().combined);
		renderer.setColor(Color.YELLOW);
		renderer.circle(position.x - 16, position.y - 16, 5); //ELA É UM CIRCULO 
	}

}
