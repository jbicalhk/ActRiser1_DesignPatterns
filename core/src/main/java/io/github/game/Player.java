package io.github.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Player extends Entity {
    public Rectangle bounds; // Para colisão
    private int kills = 0;
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
    private SpriteBatch spriteBatch;
    
    Player(float posX, float posY, SpriteBatch batch) {
        super(posX, posY);
        this.spriteBatch = batch;
        this.bounds = new Rectangle(posX, posY, 48, 48);
        this.vida = 100;
        this.dano = 10;
        this.movementStrategy = new WalkMovement(bounds, batch);
        this.attackStrategy = new PlayerAttackStrategy(bounds);
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
    
    @Override
    void update(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        movementStrategy.move(deltaTime, this);
        attackStrategy.attack(deltaTime, this, enemies, projectiles);
        // Atualizar posição
        bounds.x = posX;
        bounds.y = posY;
    }

    @Override
    void render(ShapeRenderer renderer) {
        ((WalkMovement)movementStrategy).render();
    }
}