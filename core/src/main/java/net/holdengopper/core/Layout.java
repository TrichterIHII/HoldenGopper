package net.holdengopper.core;

public class Layout {
    public final int DEFAULT_UI_SCALE = 4;

    public int ui_scale = DEFAULT_UI_SCALE;

    public boolean uiScaleLegalicity() {
        return ui_scale <= 4 && ui_scale >= 0;
    }

    public int getUiCustomX() {
        return 0;
    }

    public int getUiCustomY() {
        return 0;
    }
}
