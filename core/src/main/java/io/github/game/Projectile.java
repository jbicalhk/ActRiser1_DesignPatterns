package io.github.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

abstract class Projectile {
    Vector2 position;
    Vector2 direction;
    int dano;
	protected float lifetime;
    

    Projectile(float x, float y, Vector2 direction, int dano) {
        this.position = new Vector2(x, y);
        this.direction = direction;
        this.dano = dano;
    }
    public abstract void update(float deltaTime, Array<Enemy> enemies);

    boolean isExpired() {
        return lifetime <= 0;
    }
    public abstract void render(Camera camera, ShapeRenderer renderer);
}