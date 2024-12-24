package io.github.game;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
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



    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        player = (Player) EntityFactory.createEntity("Player", 100, 100);
        camera = new Camera();
        camera.createCamera();
        enemies = new Array<>();
        projectiles = new Array<>();
        lastEnemySpawn = TimeUtils.millis();
        buildings = new ArrayList<>();
        eventManager = new EventManager();
        mapa = new Map("mapa.tmx");

        eventManager.subscribe((event, data) -> {
            if (event.equals("EnemyKilled")) {
                player.addKill();
                if (player.getKills() % 3 == 0) {
                    Building building = EntityFactory.createBuilding();
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

        camera.updateCamera(player);
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

        if (TimeUtils.timeSinceMillis(lastEnemySpawn) > 9000) {
            spawnEnemy();
            lastEnemySpawn = TimeUtils.millis();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);
        for (Enemy enemy : enemies) {
            enemy.render(shapeRenderer);
        }
        for (Projectile projectile : projectiles) {
            projectile.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void spawnEnemy() {
        Enemy enemy = (Enemy) EntityFactory.createEntity("Enemy", 100, 100);
        enemies.add(enemy);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        mapa.dispose();
    }
}
