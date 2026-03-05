package net.holdengopper.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import net.holdengopper.core.screens.CasinoScreen;

public class HoldenGopper extends Game {

    public static HoldenGopper INSTANCE;

    public HoldenGopper() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        Pixmap original = new Pixmap(Gdx.files.internal("cursor.png"));
        Pixmap scaled = new Pixmap(0x20, 0x20, original.getFormat());

        scaled.drawPixmap(
                original,
                0, 0,
                original.getWidth(),
                original.getHeight(),
                0, 0,
                0x20,
                0x20
        );

        original.dispose();

        Cursor cursor = Gdx.graphics.newCursor(scaled, scaled.getWidth() / 2, scaled.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);
        scaled.dispose();

        setScreen(new CasinoScreen());
    }
}
