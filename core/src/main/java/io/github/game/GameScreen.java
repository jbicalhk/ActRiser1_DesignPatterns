package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    private final MainGame game;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Array<Enemy> enemies;
    private Array<Projectile> projectiles;
    private long lastEnemySpawn;
    private EventManager eventManager;
    private Map mapa;
    private Camera camera;
    private Array<HealthPickUp> vidas;
    private long lastPotionLifeSpawn;
    private TextFader texto;
    private SpriteBatch textBatch;
	private Sound newBuilding;


    public GameScreen(MainGame game) {
        this.game = game;
        create();
    }

    private void create() {
        shapeRenderer = new ShapeRenderer();
        textBatch = new SpriteBatch();
        player = (Player) EntityFactory.createEntity("Player", 656, 440, game.getSpriteBatch());
        camera = new Camera();
        camera.createCamera();
        enemies = new Array<>();
        projectiles = new Array<>();
        lastEnemySpawn = TimeUtils.millis();
        eventManager = new EventManager();
        mapa = new Map("mapa.tmx");
		newBuilding = Gdx.audio.newSound(Gdx.files.internal("sounds/newBuilding.mp3"));
        vidas = new Array<>();
        lastPotionLifeSpawn = TimeUtils.millis();
        texto = new TextFader("Uma nova construção foi adicionada!", Gdx.graphics.getWidth() / 2f - 135,
                Gdx.graphics.getHeight() / 2f + 150, 1f, 2f);

        setupEventManager();
    }

    private void setupEventManager() {
        eventManager.subscribe((event, data) -> {
            if (event.equals("EnemyKilled")) {
                player.addKill();
                int kills = player.getKills();

                if (kills >= 69) {
                    game.setScreen(new VictoryScreen(game));
                }

                if (kills % 3 == 0 && kills <= 69) {
                    int layerIndex = 5 + (kills / 3) - 1;
                    mapa.activateLayer(layerIndex);
                    texto.show();
                    newBuilding.play(0.2f);
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Check for game over
        if (player.getVida() <= 0) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        updateGame(delta);
        renderGame();
    }

    private void updateGame(float delta) {
        camera.updateCamera(player);
        player.update(delta, player, enemies, projectiles);
        updateEnemies(delta);
        updateProjectiles(delta);
        updateSpawns();
        updateHealthPickups();
    }

    private void updateEnemies(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player, null, null);
        }

        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                eventManager.enemyKilled();
                enemies.removeIndex(i);
            }
        }
    }

    private void updateProjectiles(float delta) {
        for (Projectile projectile : projectiles) {
            projectile.update(delta, enemies);
        }

        for (int i = projectiles.size - 1; i >= 0; i--) {
            if (projectiles.get(i).isExpired()) {
                projectiles.removeIndex(i);
            }
        }
    }

    private void updateSpawns() {
        if (TimeUtils.timeSinceMillis(lastEnemySpawn) > 6000) {
            spawnEnemy();
            lastEnemySpawn = TimeUtils.millis();
        }

        if (TimeUtils.timeSinceMillis(lastPotionLifeSpawn) > 18000) {
            spawnPotion();
            lastPotionLifeSpawn = TimeUtils.millis();
        }
    }

    private void updateHealthPickups() {
        for (int i = vidas.size - 1; i >= 0; i--) {
            HealthPickUp vida = vidas.get(i);
            if (player.getBounds().contains(vida.getPosition())) {
                vida.applyEffect(player);
                vidas.removeIndex(i);
            } else if (System.currentTimeMillis() - vida.getSpawnTime() > 5000) {
                vidas.removeIndex(i);
            }
        }
    }

    private void renderGame() {
        mapa.render(camera.getCamera());

        // Render health pickups
        for (HealthPickUp vida : vidas) {
            vida.render(camera, shapeRenderer);
        }

        // Render projectiles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Projectile projectile : projectiles) {
            projectile.render(camera, shapeRenderer);
        }
        shapeRenderer.end();

        texto.render(textBatch);

        // Render player and enemies
        player.render(camera, shapeRenderer);
        for (Enemy enemy : enemies) {
            enemy.render(camera, shapeRenderer);
        }
    }

    private void spawnEnemy() {
        Enemy enemy = (Enemy) EntityFactory.createEntity("Enemy", 
            MathUtils.random(144, 1114),
            MathUtils.random(144, 1114), 
            game.getSpriteBatch());
        enemies.add(enemy);
    }

    private void spawnPotion() {
        HealthPickUp vida = new HealthPickUp(
            MathUtils.random(144, 1142),
            MathUtils.random(144, 1142),
            5, 15);
        vidas.add(vida);
    }

    @Override
    public void resize(int width, int height) {
        camera.getCamera().setToOrtho(false, width / 2f, height / 2f);
        camera.getCamera().update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        mapa.dispose();
        texto.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}