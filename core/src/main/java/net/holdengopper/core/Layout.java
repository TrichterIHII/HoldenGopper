package net.holdengopper.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class Layout {
    public final int DEFAULT_UI_SCALE = 4;
    public final int MAX_UI_SCALE = 16;

    public int ui_scale = DEFAULT_UI_SCALE;

    public boolean uiScaleLegalicity() {
        return ui_scale <= 4 && ui_scale >= 0;
    }

    public int getUiCustomX() {
        int scale = MAX_UI_SCALE - ui_scale;
        return 0;
    }

    public int getUiCustomY() {
        int scale = MAX_UI_SCALE - ui_scale;
        return 0;
    }

    public int getUiCustomWidth() {
        int scale = MAX_UI_SCALE - ui_scale;
        return 0;
    }

    public int getUiCustomHeight() {
        int scale = MAX_UI_SCALE - ui_scale;
        return 0;
    }

    public int calculateUi() {
        calcScale();
        return ui_scale;
    }

    public void calcScale() {
        Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int screen_width = mode.width;
        int screen_height = mode.height;

        if ((width >= screen_width && height >= screen_height) || Gdx.graphics.isFullscreen()) {
            ui_scale = MAX_UI_SCALE;
        }


    }
}
