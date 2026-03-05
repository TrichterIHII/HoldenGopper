package net.holdengopper.app;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.holdengopper.core.HoldenGopper;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("HoldenGopper");
        config.useVsync(true);
        config.setWindowedMode(800, 800 / 16 * 9);
        config.setWindowIcon(Files.FileType.Internal, "test.jpeg");
        //config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        new Lwjgl3Application(new HoldenGopper(), config);
    }
}
