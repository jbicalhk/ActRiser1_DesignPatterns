package io.github.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;


class PlayerAnimation extends HandleAnimation {
	
	
    private Texture spriteSheet;
    private TextureRegion[][] frames;

    public PlayerAnimation(Texture spriteSheet) {
        this.spriteSheet = spriteSheet;
        this.frames = TextureRegion.split(spriteSheet, 48, 48);
    }

    public void setIdleAnimation() {
        setAnimation(getFrames(0, 2), 0.2f);
    }

    public void setMoveAnimation(int direction) {
        setAnimation(getFrames(3 + direction, 3 + direction), 0.1f);
    }

    public void setAttackAnimation(int direction) {
        setAnimation(getFrames(6 + direction, 6 + direction), 0.15f);
    }

    private TextureRegion[] getFrames(int startRow, int endRow) {
        Array<TextureRegion> frameList = new Array<>();
        for (int row = startRow; row <= endRow; row++) {
            for (int col = 0; col < frames[row].length; col++) {
                frameList.add(frames[row][col]);
            }
        }
        return frameList.toArray(TextureRegion.class);
    }
}