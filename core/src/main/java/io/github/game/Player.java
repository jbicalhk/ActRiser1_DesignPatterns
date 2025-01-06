package io.github.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Player extends Entity {
    public Rectangle bounds; // Para colisão
    private int kills = 0;
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
	private HealthBar healthBar;
    
    Player(float posX, float posY, SpriteBatch batch) {
        super(posX, posY);
        this.bounds = new Rectangle(posX, posY, 48, 48);
        this.vida = 100;
        this.dano = 10;
        this.movementStrategy = new WalkMovement(bounds, batch);
        this.attackStrategy = new PlayerAttackStrategy(bounds);
        this.healthBar = new HealthBar(posX, posY + bounds.height + 5, 55, 4 , this.vida);
    }

    public void addKill() {
        kills++;
        System.out.println("Inimigos derrotados: " + kills);
    }

    public int getKills() {
        return kills;
    }


    public Rectangle getBounds() {
        return bounds;
    }
    public HealthBar getHealthBar() {
        return healthBar;
    }
    
    @Override
    void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        movementStrategy.move(deltaTime, this);
        attackStrategy.attack(deltaTime, this, enemies, projectiles);
        // Sincronizar todas as posições
        bounds.x = posX;
        bounds.y = posY;
        healthBar.setPosition(posX -20, posY - 10);
    }

    void render(Camera camera, ShapeRenderer renderer) {
        // Only render health bar with ShapeRenderer
        healthBar.setHealth(this.vida);
        healthBar.render(camera, renderer);
        
        // Movement/animation rendering is handled by WalkMovement
        ((WalkMovement)movementStrategy).render(camera);
    }

	@Override
	void render(ShapeRenderer renderer) {
		// TODO Auto-generated method stub
		
	}
	
	public void increaseLife(int num) {
		vida += num;
	}

	public Vector2 getPosition() {
	    return new Vector2(posX, posY);
	}
}