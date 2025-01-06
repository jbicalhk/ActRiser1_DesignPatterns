// Atualização da classe WalkMovement para o spritesheet fornecido
package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
        
        float newPosX = player.posX;
        float newPosY = player.posY;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newPosY += speed * deltaTime;
            direction = PlayerAnimation.Direction.UP;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newPosY -= speed * deltaTime;
            direction = PlayerAnimation.Direction.DOWN;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newPosX -= speed * deltaTime;
            direction = PlayerAnimation.Direction.LEFT;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newPosX += speed * deltaTime;
            direction = PlayerAnimation.Direction.RIGHT;
            isMoving = true;
        }

        // Apply boundary checks before updating position
        if (newPosX >= 144 && newPosX <= 1142 - rect.width) {
            player.posX = newPosX;
        }
        if (newPosY >= 144 && newPosY <= 1142 - rect.height) {
            player.posY = newPosY;
        }

        state = isMoving ? PlayerAnimation.PlayerState.MOVING : PlayerAnimation.PlayerState.IDLE;
        animation.update(deltaTime, direction, state);
    

        state = isMoving ? PlayerAnimation.PlayerState.MOVING : PlayerAnimation.PlayerState.IDLE;
        animation.update(deltaTime, direction, state);
    }

    public void render(Camera camera) {
        batch.begin();
        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.draw(animation.getCurrentFrame(), rect.x, rect.y);
        batch.end();
    }

    public void dispose() {
        animation.dispose();
    }
}
