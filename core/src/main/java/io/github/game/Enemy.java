package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

class Enemy extends Entity {
    private Rectangle bounds; // For collision detection
    private float health = 100;
    private Random random = new Random();
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
    private EnemyAnimation animation;
    private SpriteBatch batch;
    
    Enemy(float posX, float posY) {
        super(posX, posY);
        this.bounds = new Rectangle(posX, posY, 16, 16);
        this.vida = 50;
        this.dano = 5;
        this.movementStrategy = new EnemyMovement(bounds);
        this.attackStrategy = new EnemyAttackStrategy(bounds);
        this.animation = new EnemyAnimation("enemy.png");
        this.batch = new SpriteBatch();
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
    }

    void takeDamage(float damage) {
        health -= damage;
    }

    boolean isDead() {
        return health <= 0;
    }
    
    @Override
    void render(ShapeRenderer renderer) {
        // We don't use ShapeRenderer anymore, but we need to keep this method
        // for compatibility with the Entity class
    }

    // New render method that uses SpriteBatch
    void render() {
        batch.begin();
        batch.draw(animation.getCurrentFrame(), posX, posY);
        batch.end();
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