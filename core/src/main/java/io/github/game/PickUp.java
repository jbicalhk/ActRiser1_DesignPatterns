package io.github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class PickUp {
	protected Vector2 position;
	protected float tam;
	private long spawnTime;

	public PickUp(float x, float y, float tam) {
		this.position = new Vector2(x, y);
		this.tam = tam;
		this.spawnTime = System.currentTimeMillis();
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getTam() {
		return tam;
	}

	public abstract void applyEffect(Player player);

	public void render(Camera camera, ShapeRenderer shapeRenderer) {
		shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(position.x, position.y, tam);
		shapeRenderer.end();
	}

	public long getSpawnTime() {
		return spawnTime;
	}
}
