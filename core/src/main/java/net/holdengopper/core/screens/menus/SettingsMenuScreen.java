package net.holdengopper.core.screens.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.holdengopper.core.button.Button;

public class SettingsMenuScreen extends MenuScreen {
    public SpriteBatch batch;

    @SuppressWarnings("all")
    private final int BUTTON_WIDTH  = 120;
    @SuppressWarnings("all")
    private final int BUTTON_HEIGHT = 30;

    public SettingsMenuScreen() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
