package io.github.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

abstract class Entity {
	protected int vida;
    protected int dano;
    protected float posX;
    protected float posY;
  
	public Entity(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}


	//abstract void update(float deltaTime, Array<Entity> entities);
    abstract void render(ShapeRenderer renderer);
    

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	abstract void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles);
	
}
 