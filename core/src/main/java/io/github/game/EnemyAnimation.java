package io.github.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class EnemyAnimation {
    private static final int FRAME_WIDTH = 16;
    private static final int FRAME_HEIGHT = 16;
    
    private Texture spriteSheet;
    private Animation<TextureRegion> animation;
    private float stateTime;

    public EnemyAnimation(String spritePath) {
        spriteSheet = new Texture(spritePath);
        
        // Create animation frames
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, FRAME_WIDTH, FRAME_HEIGHT);
        Array<TextureRegion> frames = new Array<>();
        
        // Since the spritesheet is 80x17, we have 5 frames (80/16 = 5)
        for(int i = 0; i < 5; i++) {
            frames.add(tmp[0][i]);
        }
        
        animation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        stateTime = 0;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public TextureRegion getCurrentFrame() {
        return animation.getKeyFrame(stateTime, true);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}