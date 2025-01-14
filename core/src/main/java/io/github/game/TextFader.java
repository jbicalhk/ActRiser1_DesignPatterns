package io.github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class TextFader {
    private BitmapFont font;
    private String text;
    private float x, y;
    private float fadeInDuration, fadeOutDuration;
    private long startTime;
    private boolean active;

    public TextFader(String text, float x, float y, float fadeInDuration, float fadeOutDuration) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fadeInDuration = fadeInDuration;
        this.fadeOutDuration = fadeOutDuration;
        this.active = false;

        // Usando a fonte padrão do LibGDX
        font = new BitmapFont();
        font.setColor(new Color(1, 1, 1, 1)); //Inicialmente transparente
    }

    public void show() {
        this.startTime = TimeUtils.millis();
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        if (!active) return;

        float elapsedTime = (TimeUtils.millis() - startTime) / 1000f;
        Color color = font.getColor();

        if (elapsedTime < fadeInDuration) {
            // Fade In
            color.a = elapsedTime / fadeInDuration;
        } else if (elapsedTime < fadeInDuration + fadeOutDuration) {
            // Fade Out
            float fadeOutTime = elapsedTime - fadeInDuration;
            color.a = 1 - (fadeOutTime / fadeOutDuration);
        } else {
            // Termina o fade
            active = false;
            return;
        }

        font.setColor(color);

        // Renderizar texto diretamente em coordenadas de tela
        batch.begin();
        font.draw(batch, text, x, y); // Usa as coordenadas da janela
        batch.end();
    }


    public void dispose() {
        font.dispose();
    }
}
