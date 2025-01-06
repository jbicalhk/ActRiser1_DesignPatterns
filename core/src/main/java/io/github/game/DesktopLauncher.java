package io.github.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
	private Map mapa;
	private Camera camera;
	private SpriteBatch spriteBatch;
	private Array<HealthPickUp> vidas;
	private long lastPotionLifeSpawn;
	private Sound newBuilding;
	private TextFader texto;
	private SpriteBatch textBatch;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		textBatch = new SpriteBatch();
		player = (Player) EntityFactor.createEntity("Player", 656, 440, spriteBatch);
		camera = new Camera();
		camera.createCamera();
		enemies = new Array<>();
		projectiles = new Array<>();
		lastEnemySpawn = TimeUtils.millis();
		eventManager = new EventManager();
		mapa = new Map("mapa.tmx");
		vidas = new Array<>();
		lastPotionLifeSpawn = TimeUtils.millis();
		newBuilding = Gdx.audio.newSound(Gdx.files.internal("sounds/newBuilding.mp3"));

		texto = new TextFader("Uma nova construção foi adicionada!", Gdx.graphics.getWidth() / 2f - 135,
				Gdx.graphics.getHeight() / 2f + 150, 1f, 2f);

		eventManager.subscribe((event, data) -> {
			if (event.equals("EnemyKilled")) {
				player.addKill();
				int kills = player.getKills();

				if (kills % 3 == 0 && player.getKills() <= 69) {
					int layerIndex = 5 + (kills / 3) - 1;
					mapa.activateLayer(layerIndex);
					System.out.println("Nova construção adicionada ao mapa!");
					newBuilding.play(0.2f);
					texto.show();
				}
			}
		});
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();

		camera.updateCamera(player);

		// Renderizar mapa
		mapa.render(camera.getCamera());

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
			projectile.render(camera, shapeRenderer);
		}
		shapeRenderer.end();

		texto.render(textBatch); // Renderiza o texto com fade

		// Renderizar player e inimigos
		player.render(camera, shapeRenderer);

		for (Enemy enemy : enemies) {
			enemy.render(camera, shapeRenderer);
			// enemy.getBatch().setProjectionMatrix(camera.getCamera().combined);
		}

		// System.out.println(player.posY);
	}

	private void spawnEnemy() {
		Enemy enemy = (Enemy) EntityFactor.createEntity("Enemy", MathUtils.random(144, 1114),
				MathUtils.random(144, 1114), spriteBatch);
		enemies.add(enemy);
	}

	private void spawnPotion() {
		HealthPickUp vida = new HealthPickUp(MathUtils.random(144, 1142), MathUtils.random(144, 1142), 5, 15);
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
		texto.dispose();
	}
}
