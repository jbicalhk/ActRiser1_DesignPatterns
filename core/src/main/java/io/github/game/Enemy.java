package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class Enemy extends Entity {
    //private Rectangle bounds; // For collision detection
    private float health = 100;
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
    private EnemyAnimation animation;
    private SpriteBatch batch;
    private HealthBar healthBar;
    
    Enemy(float posX, float posY, SpriteBatch batch) {
        super(posX, posY);
        this.bounds = new Rectangle(posX, posY, 16, 16);
        this.vida = 50;
        this.dano = 5;
        this.movementStrategy = new EnemyMovement(bounds);
        this.attackStrategy = new EnemyAttackStrategy(bounds);
        this.animation = new EnemyAnimation("enemy.png");
        this.batch = batch;
        this.healthBar = new HealthBar(posX, posY + bounds.height + 5, bounds.width, 4, health);
    }

    @Override
    void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        if (health <= 0) return;

        ((EnemyMovement) movementStrategy).move(deltaTime, player);
        attackStrategy.attack(deltaTime, player, null, null);
        animation.update(deltaTime);
        
        // Update position
        posX = bounds.x;
        posY = bounds.y;
        healthBar.setPosition(posX, posY + bounds.height + 5);
    }

    void takeDamage(float damage) {
        health -= damage;
        healthBar.setHealth(health);
    }

    boolean isDead() {
        return health <= 0;
    }
    

    @Override
    void render(Camera camera, ShapeRenderer renderer) {
    	batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();
        batch.draw(animation.getCurrentFrame(), posX, posY);
        batch.end();
        healthBar.render(camera, renderer);
    }
  

    public void dispose() {
        animation.dispose();
        batch.dispose();
    }    

    // Getter for bounds (for collision detection)
    public Rectangle getBounds() {
        return bounds;
    }
}