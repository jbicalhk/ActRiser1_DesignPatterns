package io.github.game;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    private Camera camera1;
	private Map mapa;


    @Override
    public void create() {
    	//camera
    	mapa = new Map();
    	shapeRenderer = new ShapeRenderer();
    	player = (Player) EntityFactory.createEntity("Player");
    	camera1 = new Camera();
    	camera1.createACamera(player);
    	
    	
        //player = new Player(new Rectangle(100, 100, 40, 40));
        enemies = new Array<>();
        projectiles = new Array<>();
        lastEnemySpawn = TimeUtils.millis();
        buildings = new ArrayList<>();
        
        
        eventManager = new EventManager();
        eventManager.subscribe((event, data) -> {
            if (event.equals("EnemyKilled")) {
                player.addKill();
                if (player.getKills() % 3 == 0) { // A cada 3 inimigos mortos, adiciona uma construção
                    Building building = EntityFactory.createBuilding();
                    buildings.add(building);
                    System.out.println("Nova construção adicionada ao mapa!");
                }
            }
        });
    }


    @Override
    public void render() {
    	
    	//Saber em tempo real qunatas kills o player tem
    	//System.out.println(player.getKills());
    	
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapa.render();
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Update entities
        player.update(deltaTime, player, enemies, projectiles);
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime, player, enemies, projectiles);
        }
        for (Projectile projectile : projectiles) {
            projectile.update(deltaTime, enemies);
        }

        // Remove dead enemies
        //PLAYER MORTO
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                // Dispara o evento de "EnemyKilled" apenas uma vez quando o inimigo for morto
                eventManager.enemyKilled();
                
                // Remove o inimigo da lista
                enemies.removeIndex(i);
                
            }
        }
        
        // Remove expired projectiles
        for (int i = projectiles.size - 1; i >= 0; i--) {
            if (projectiles.get(i).isExpired()) {
                projectiles.removeIndex(i);
            }
        }

        // Spawn enemies every 9 seconds
        if (TimeUtils.timeSinceMillis(lastEnemySpawn) > 9000) {
            spawnEnemy();
            lastEnemySpawn = TimeUtils.millis();
        }

        // Render entities
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
        //float x = MathUtils.random(0, Gdx.graphics.getWidth() - 40);
        //float y = MathUtils.random(0, Gdx.graphics.getHeight() - 40);
    	Enemy enemy = (Enemy) EntityFactory.createEntity("Enemy");
        enemies.add(enemy);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        mapa.dispose();
    }
}
