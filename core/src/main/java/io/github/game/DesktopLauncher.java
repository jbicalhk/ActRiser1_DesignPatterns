package io.github.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class DesktopLauncher extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private Player player;
	private Array<Enemy> enemies;
	private Array<Projectile> projectiles;
	private long lastEnemySpawn;
	private EventManager eventManager;
	private List<Building> buildings;
	private Map mapa;
	private Camera camera;
	private SpriteBatch spriteBatch;
	private Array<HealthPickUp> vidas;
	private long lastPotionLifeSpawn;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		player = (Player) EntityFactor.createEntity("Player", 200, 100, spriteBatch);
		camera = new Camera();
		camera.createCamera();
		enemies = new Array<>();
		projectiles = new Array<>();
		lastEnemySpawn = TimeUtils.millis();
		buildings = new ArrayList<>();
		eventManager = new EventManager();
		mapa = new Map("mapa.tmx");
		vidas = new Array<>();
		lastPotionLifeSpawn= TimeUtils.millis();
		
		
		eventManager.subscribe((event, data) -> {
			if (event.equals("EnemyKilled")) {
				player.addKill();
				if (player.getKills() % 3 == 0) {
					Building building = EntityFactor.createBuilding();
					buildings.add(building);
					System.out.println("Nova construção adicionada ao mapa!");
				}
			}
		});
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Atualizar a projeção do SpriteBatch e ShapeRenderer
        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        camera.updateCamera(player);
        

        // Renderizar mapa
        mapa.render(camera.getCamera());
		// Begin shape renderer with the camera's projection matrix

		player.update(deltaTime, player, enemies, projectiles);
		for (Enemy enemy : enemies) {
			enemy.update(deltaTime, player, null, null);
		}
		for (Projectile projectile : projectiles) {
			projectile.update(deltaTime, enemies);
		}

		for (int i = enemies.size - 1; i >= 0; i--) {
			Enemy enemy = enemies.get(i);
			if (enemy.isDead()) {
				eventManager.enemyKilled();
				enemies.removeIndex(i);
			}
		}

		for (int i = projectiles.size - 1; i >= 0; i--) {
			if (projectiles.get(i).isExpired()) {
				projectiles.removeIndex(i);
			}
		}
		
		for (HealthPickUp vida : vidas) {
		    vida.render(camera, shapeRenderer);
		}
		
		
		
		if (TimeUtils.timeSinceMillis(lastEnemySpawn) > 6000) {
            spawnEnemy();
            lastEnemySpawn = TimeUtils.millis();
        }
		
		if (TimeUtils.timeSinceMillis(lastPotionLifeSpawn) > 18000) {
            spawnPotion();
            lastPotionLifeSpawn = TimeUtils.millis();
        }
		
		for (int i = vidas.size - 1; i >= 0; i--) {
	        HealthPickUp vida = vidas.get(i);

	        // Verifica colisão com o jogador
	        if (player.getBounds().contains(vida.getPosition())) {
	            vida.applyEffect(player); // Chama o método applyEffect
	            vidas.removeIndex(i); // Remove a poção da lista após o efeito
	        }

	        // Remove a poção se passou do tempo limite (5 segundos)
	        if (System.currentTimeMillis() - vida.getSpawnTime() > 5000) {
	            vidas.removeIndex(i);
	        } else {
	            vida.render(camera, shapeRenderer); // Renderiza a poção
	        }
	    }

        // Renderizar projéteis
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Projectile projectile : projectiles) {
            projectile.render(camera ,shapeRenderer);
        }
        shapeRenderer.end();

        // Renderizar player e inimigos
        player.render(camera, shapeRenderer);
        
        for (Enemy enemy : enemies) {
            enemy.render(camera, shapeRenderer);
            //enemy.getBatch().setProjectionMatrix(camera.getCamera().combined);
        }
        
    //    System.out.println(player.posY);
    }

	private void spawnEnemy() {
		Enemy enemy = (Enemy) EntityFactor.createEntity("Enemy", MathUtils.random(0, 943), MathUtils.random(0, 943), spriteBatch);
		enemies.add(enemy);
	}
	private void spawnPotion() {
		HealthPickUp vida = new HealthPickUp(MathUtils.random(0, 943), MathUtils.random(0, 943), 5, 15);
		vidas.add(vida);
	}

	@Override
	public void dispose() {
		for (int i = enemies.size - 1; i >= 0; i--) {
		    Enemy enemy = enemies.get(i);
		    if (enemy.isDead()) {
		        enemy.dispose(); // Add this line
		        eventManager.enemyKilled();
		        enemies.removeIndex(i);
		    }
		}
		shapeRenderer.dispose();
		mapa.dispose();
	}
}
