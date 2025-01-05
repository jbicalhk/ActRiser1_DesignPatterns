package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WalkMovement implements MovementStrategy {
    private Rectangle rect;
    private float speed = 200f;
    private PlayerAnimation animation;
    private SpriteBatch batch;

    public WalkMovement(Rectangle rect, SpriteBatch batch) {
        this.rect = rect;
        this.batch = batch;
        this.animation = new PlayerAnimation("player.png");
    }

    @Override
    public void move(float deltaTime, Player player) {
        boolean isMoving = false;
        PlayerAnimation.Direction direction = PlayerAnimation.Direction.DOWN;
        PlayerAnimation.PlayerState state = PlayerAnimation.PlayerState.IDLE;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.posY += speed * deltaTime;
            direction = PlayerAnimation.Direction.UP;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	player.posY -= speed * deltaTime;
            direction = PlayerAnimation.Direction.DOWN;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        	player.posX -= speed * deltaTime;
            direction = PlayerAnimation.Direction.LEFT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	player.posX += speed * deltaTime;
            direction = PlayerAnimation.Direction.RIGHT;
            isMoving = true;
        }

        state = isMoving ? PlayerAnimation.PlayerState.MOVING : PlayerAnimation.PlayerState.IDLE;
        animation.update(deltaTime, direction, state);
       
    }

    public void render() {
        batch.begin();
        batch.draw(animation.getCurrentFrame(), rect.x, rect.y);
        batch.end();
    }

    public void dispose() {
        animation.dispose();
    }
}