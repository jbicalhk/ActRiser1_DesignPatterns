package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private Stage stage;
    private BitmapFont font;
    private GlyphLayout layout;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(960, Gdx.graphics.getHeight()));// ISSO DAQUI MUDAR EM TUDO 
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();
        
        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    private void createUI() {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        
        TextButton playButton = new TextButton("Play", style);
        playButton.setPosition(400 - playButton.getWidth() / 2, 300 - playButton.getHeight() / 2);
        
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        
        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        game.getSpriteBatch().begin();
        layout.setText(font, "Welcome to the Game!");
        font.draw(game.getSpriteBatch(), layout, 
            400 - layout.width / 2,
            400);
        game.getSpriteBatch().end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}