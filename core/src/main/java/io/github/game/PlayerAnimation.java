package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;


public class PlayerAnimation {
    private static final int SPRITE_WIDTH = 48;
    private static final int SPRITE_HEIGHT = 48;
    private static final int IDLE_START_ROW = 0;
    private static final int MOVE_START_ROW = 3;
    private static final int ATTACK_START_ROW = 6;
    private static final int DEATH_ROW = 9;

    private Texture spriteSheet;
    private TextureRegion[][] frames;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private Direction currentDirection;
    private PlayerState currentState;

    public enum Direction {
        UP(0), DOWN(1), LEFT(2), RIGHT(3);
        
        final int index;
        Direction(int index) { this.index = index; }
    }

    public enum PlayerState {
        IDLE, MOVING, ATTACKING, DEAD
    }

    public PlayerAnimation(String spritePath) {
        spriteSheet = new Texture(spritePath);
        frames = TextureRegion.split(spriteSheet, SPRITE_WIDTH, SPRITE_HEIGHT);
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
    	int row;
    	switch (state) {
        case IDLE:
            row = IDLE_START_ROW + direction.index;
            break;
        case MOVING:
            row = MOVE_START_ROW + direction.index;
            break;
        case ATTACKING:
            row = ATTACK_START_ROW + direction.index;
            break;
        case DEAD:
            row = DEATH_ROW;
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + state);
    }

        Array<TextureRegion> animationFrames = new Array<>();
        for (int col = 0; col < frames[row].length; col++) {
            TextureRegion frame = frames[row][col];
            if (direction == Direction.LEFT) {
                frame = new TextureRegion(frame);
                frame.flip(true, false);
            }
            animationFrames.add(frame);
        }

        float frameDuration = state == PlayerState.MOVING ? 0.1f : 0.2f;
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