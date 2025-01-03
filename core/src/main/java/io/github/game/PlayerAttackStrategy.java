package io.github.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerAttackStrategy implements AttackStrategy {
    private Rectangle rect;
    private float attackCooldown;
    private float projectileCooldown;
    private Camera camera;

    public PlayerAttackStrategy(Rectangle rect) {
        this.rect = rect;
        this.attackCooldown = 0;
        this.projectileCooldown = 0;
    }

    @Override
    public void attack(float deltaTime, Player player, Array<Enemy> enemies, Array<Projectile> projectiles) {
        // Melee Attack
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && attackCooldown <= 0) {
            for (Enemy enemy : enemies) {
                if (rect.overlaps(enemy.rect)) {
                    enemy.takeDamage(15);
                }
            }
            attackCooldown = 1.2f;
        }

        // Projectile Attack
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && projectileCooldown <= 0) {
            @SuppressWarnings("static-access")
			Vector2 mouseWorldPos = camera.getWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());

            Vector2 direction = mouseWorldPos.sub(new Vector2(rect.x + rect.width / 2, rect.y + rect.height / 2)).nor();
            projectiles.add(new Bullet(rect.x + rect.width / 2, rect.y + rect.height / 2, direction, 50, 500, 2f));
            projectileCooldown = 2f;
        }

        attackCooldown -= deltaTime;
        projectileCooldown -= deltaTime;
    }
}