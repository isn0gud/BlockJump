package com.games.djc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.games.djc.DjcGameManager;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 600 / 2;
        config.height = 1000 / 2;
        new LwjglApplication(new DjcGameManager(new HighscoreImpl()), config);
    }
}
