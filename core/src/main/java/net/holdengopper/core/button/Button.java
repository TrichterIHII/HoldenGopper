package net.holdengopper.core.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Button {
    private final Rectangle button;
    private final Texture texture;
    private final String text;
    private final SpriteBatch batch;

    private final BitmapFont font;
    private final GlyphLayout layout;

    private ButtonListener listener;

    public Button(@NotNull Rectangle button, Texture texture, String text, @NotNull SpriteBatch batch) {
        this.button = button;
        this.texture = texture;
        this.text = text;
        this.batch = batch;

        this.font = new BitmapFont();
        this.layout = new GlyphLayout();

        layout.setText(font, text);
    }

    public void render() {
        batch.draw(texture, button.x, button.y, button.width, button.height);

        float x = button.x + (button.width >> 1) - layout.width / 2;
        float y = button.y + (button.height >> 1) + layout.height / 2;
        font.draw(batch, text, x, y);
    }

    public void addListener(ButtonListener listener) {
        this.listener = listener;
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (button.contains(mouseX, mouseY) && listener != null) {
                listener.onClick(new ButtonEvent(this));
            }
        }
    }
}
