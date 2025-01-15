package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class VictoryScreen implements Screen {
	private final MainGame game;
	private Stage stage;
	private BitmapFont font;
	private GlyphLayout layout;
	private Texture backgroundTexture;

	public VictoryScreen(MainGame game) {
		this.game = game;
		this.stage = new Stage(new FitViewport(960, 580));
		this.layout = new GlyphLayout();
		this.backgroundTexture = new Texture("backgroundMAIN.png");
		this.font = createCustomFont();

		Gdx.input.setInputProcessor(stage);
		createUI();
	}

	private BitmapFont createCustomFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 70;
		parameter.color = new com.badlogic.gdx.graphics.Color(0xcc / 255f, 0xae / 255f, 0x76 / 255f, 1);
		BitmapFont customFont = generator.generateFont(parameter);
		generator.dispose();
		return customFont;
	}

	private void createUI() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;

		TextButton playButton = new TextButton("Play", style);
		playButton.setPosition(408, 115);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				Gdx.input.setInputProcessor(null);
				game.setScreen(new GameScreen(game));
			}
		});

		stage.addActor(playButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getSpriteBatch().setProjectionMatrix(stage.getCamera().combined);
		game.getSpriteBatch().begin();
		game.getSpriteBatch().draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(),
				stage.getViewport().getWorldHeight());
		game.getSpriteBatch().end();

		stage.act(delta);
		stage.draw();

		game.getSpriteBatch().begin();
		layout.setText(font, "Você Venceu!");
		font.draw(game.getSpriteBatch(), layout, stage.getViewport().getWorldWidth() / 2 - layout.width / 2,
				stage.getViewport().getWorldHeight() / 2 + 130);
		game.getSpriteBatch().end();
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();
		font.dispose();
		backgroundTexture.dispose();
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
}
