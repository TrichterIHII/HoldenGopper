package net.holdengopper.core.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Button {
    private final Rectangle button;
    private final Texture texture;
    private final String text;
    private final SpriteBatch batch;

    private float mouseX;
    private float mouseY;

    private ButtonListener listener;

    public Button(@NotNull Rectangle button, Texture texture, String text, @NotNull SpriteBatch batch) {
        this.button = button;
        this.texture = texture;
        this.text = text;
        this.batch = batch;
    }

    public void render() {
        batch.draw(texture, button.x, button.y, button.width, button.height);
    }

    public void addListener(ButtonListener listener) {
        this.listener = listener;
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            mouseX = Gdx.input.getX();
            mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (button.contains(mouseX, mouseY) && listener != null) {
                listener.onClick(new ButtonEvent(this));
            }
        }
    }
}
