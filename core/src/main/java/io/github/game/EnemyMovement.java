package io.github.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class EnemyMovement implements MovementStrategy {
	private Rectangle bounds;
	private Direction currentDirection;
	private float moveDuration;
	private float elapsedTime;
	private Random random;

	public EnemyMovement(Rectangle bounds) {
		this.bounds = bounds;
		this.random = new Random();
		this.currentDirection = Direction.randomDirection();
		this.moveDuration = random.nextFloat() * 2 + 1;
	}

	@Override
	public void move(float deltaTime, Player player) {
		Vector2 playerPosition = new Vector2(player.getPosX(), player.getPosY());
		Vector2 enemyPosition = new Vector2(bounds.getX(), bounds.getY());
		float newX = bounds.x;
		float newY = bounds.y;

		if (enemyPosition.dst(playerPosition) < 100) {
			Vector2 direction = playerPosition.sub(enemyPosition).nor();
			newX += direction.x * 100 * deltaTime;
			newY += direction.y * 100 * deltaTime;
		} else {
			elapsedTime += deltaTime;

			if (elapsedTime > moveDuration) {
				currentDirection = Direction.randomDirection();
				moveDuration = random.nextFloat() * 2 + 1;
				elapsedTime = 0;
			}

			if (currentDirection != Direction.NONE) {
				Vector2 movement = currentDirection.getVector();
				newX += movement.x * 50 * deltaTime;
				newY += movement.y * 50 * deltaTime;
			}
		}

		if (newX >= 144 && newX <= 1114 - bounds.width) {
			bounds.x = newX;
		}
		if (newY >= 144 && newY <= 1114 - bounds.height) {
			bounds.y = newY;

		}
	}

	private enum Direction {
		UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0), NONE(0, 0);

		private final int dx;
		private final int dy;

		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public Vector2 getVector() {
			return new Vector2(dx, dy);
		}

		public static Direction randomDirection() {
			Direction[] values = Direction.values();
			return values[new Random().nextInt(values.length)];
		}
	}
}
