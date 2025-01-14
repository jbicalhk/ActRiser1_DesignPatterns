package io.github.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        getScreen().dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
