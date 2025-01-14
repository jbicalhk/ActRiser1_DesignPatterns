package io.github.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

abstract class Entity {
    protected int vida;
    protected int dano;
    protected float posX;
    protected float posY;
    protected Rectangle bounds; 
    
    public Entity(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.bounds = new Rectangle(posX, posY, 16, 16); // Tamanho padrão
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    abstract void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles);

    protected void updatePosition(float x, float y) {
        this.posX = x;
        this.posY = y;
        if (this.bounds != null) {
            this.bounds.x = x;
            this.bounds.y = y;
        }
    }

	abstract void render(Camera camera, ShapeRenderer renderer);
}