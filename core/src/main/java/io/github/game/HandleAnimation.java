package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

// Classe base para manipular animações
public class HandleAnimation {
    protected Animation<TextureRegion> animation;
    protected float stateTime;

    public HandleAnimation() {
        this.stateTime = 0f;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public TextureRegion getCurrentFrame() {
        return animation.getKeyFrame(stateTime, true);
    }

    public void setAnimation(TextureRegion[] frames, float frameDuration) {
        this.animation = new Animation<>(frameDuration, new Array<>(frames));
        this.stateTime = 0f;
    }
}