package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WalkMovement implements MovementStrategy {
	Rectangle rect;
	Vector2 direction;
	float speed;
	//float deltaTime;
	
    public WalkMovement(Rectangle rect) {
		super();
		this.rect = rect;
	}

	@Override
	public void move(float deltaTime,Player player) {
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			rect.y += 200 * deltaTime;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			rect.y -= 200 * deltaTime;
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			rect.x -= 200 * deltaTime;
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			rect.x += 200 * deltaTime;
		
	}

}
