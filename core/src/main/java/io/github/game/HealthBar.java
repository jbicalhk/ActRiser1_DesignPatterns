package io.github.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class HealthBar {
    private float x, y, width, height;
    private float maxHealth;
    private float currentHealth;

    public HealthBar(float x, float y, float width, float height, float maxHealth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void setHealth(float health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void render(Camera camera ,ShapeRenderer shapeRenderer) {
        // Renderizar o contorno da barra
    	shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        // Renderizar a barra de vida
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        float healthWidth = (currentHealth / maxHealth) * width;
        shapeRenderer.rect(x, y, healthWidth, height);
        shapeRenderer.end();
    }

	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	

}
