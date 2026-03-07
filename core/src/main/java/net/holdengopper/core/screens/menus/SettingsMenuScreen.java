package net.holdengopper.core.screens.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsMenuScreen extends MenuScreen {
    public SpriteBatch batch;

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
