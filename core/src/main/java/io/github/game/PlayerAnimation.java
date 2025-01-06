package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimation {
    private static final int SPRITE_WIDTH = 96 / 6; // Largura de cada sprite (16)
    private static final int SPRITE_HEIGHT = 19; // Altura de cada sprite

    private Texture spriteSheet;
    private TextureRegion[] frames;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private Direction currentDirection;
    private PlayerState currentState;

    public enum Direction {
        DOWN(0, 1), RIGHT(2, 3), LEFT(2, 3), UP(4, 5);

        final int startFrame;
        final int endFrame;

        Direction(int startFrame, int endFrame) {
            this.startFrame = startFrame;
            this.endFrame = endFrame;
        }
    }

    public enum PlayerState {
        IDLE, MOVING
    }

    public PlayerAnimation(String spritePath) {
        spriteSheet = new Texture(spritePath);

        // Dividir o spritesheet em uma linha de 6 sprites
        TextureRegion fullSheet = new TextureRegion(spriteSheet);
        frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            frames[i] = new TextureRegion(
                fullSheet, i * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT
            );
        }

        currentDirection = Direction.DOWN;
        currentState = PlayerState.IDLE;
        stateTime = 0;
        setAnimation(PlayerState.IDLE, Direction.DOWN);
    }

    public void update(float deltaTime, Direction direction, PlayerState state) {
        stateTime += deltaTime;

        if (currentDirection != direction || currentState != state) {
            setAnimation(state, direction);
            currentDirection = direction;
            currentState = state;
        }
    }

    private void setAnimation(PlayerState state, Direction direction) {
        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = direction.startFrame; i <= direction.endFrame; i++) {
            TextureRegion frame = frames[i];
            if (direction == Direction.LEFT) {
                frame = new TextureRegion(frame);
                frame.flip(true, false); // Inverter para LEFT
            }
            animationFrames.add(frame);
        }

        float frameDuration = state == PlayerState.MOVING ? 0.1f : 0.5f; // Duração dos frames
        currentAnimation = new Animation<>(frameDuration, animationFrames, Animation.PlayMode.LOOP);
        stateTime = 0;
    }

    public TextureRegion getCurrentFrame() {
        return currentAnimation.getKeyFrame(stateTime, true);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
