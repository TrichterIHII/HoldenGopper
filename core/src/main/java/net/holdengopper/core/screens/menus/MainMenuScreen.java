package net.holdengopper.core.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.holdengopper.core.HoldenGopper;
import net.holdengopper.core.button.Button;
import net.holdengopper.core.screens.CasinoScreen;

import java.awt.*;

public class MainMenuScreen extends MenuScreen {
    private final SpriteBatch batch;

    private final Button play_button;
    private final Button settings_button;

    @SuppressWarnings("all")
    private final int BUTTON_WIDTH  = 120;
    @SuppressWarnings("all")
    private final int BUTTON_HEIGHT = 30;


    public MainMenuScreen() {
        batch = new SpriteBatch();

        int x = Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2;
        int y = Gdx.graphics.getHeight() / 2 - BUTTON_HEIGHT / 2;

        play_button = new Button(
                new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT),
                new Texture("assets/textures/menu/main/play_button.png"),
                "PLAY", batch);

        settings_button = new Button(
                new Rectangle(x, y - 50, BUTTON_WIDTH, BUTTON_HEIGHT),
                new Texture("assets/textures/menu/main/play_button.png"),
                "SETTINGS", batch);

        play_button.addListener(e -> HoldenGopper.INSTANCE.setScreen(new CasinoScreen()));
        settings_button.addListener(e -> HoldenGopper.INSTANCE.setScreen(new SettingsMenuScreen()));
    }

    @Override
    public void render(float delta) {
        batch.begin();
            play_button.render();
            settings_button.render();
        batch.end();

        play_button.update();
        settings_button.update();
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
