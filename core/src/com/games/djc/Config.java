package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Pius on 17.01.2015.
 */
public class Config {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;
    public static final String gameTitle = "gameTitle";
    public static Highscore highscore = null;

    public static Config instance = null;

    private Config() {
    }

    public synchronized static Config getInstance() {
        if (Config.instance == null) {
            Config.instance = new Config();
        }
        return Config.instance;
    }


    public final static int[] highscores = new int[]{100, 80, 50, 30, 10};
    public final static String file = ".djcSave";

    public static void load() {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            String[] strings = filehandle.readString().split("\n");

            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(strings[i + 1]);
            }
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save() {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            for (int i = 0; i < 5; i++) {
                filehandle.writeString(Integer.toString(highscores[i]) + "\n", true);
            }
        } catch (Throwable e) {
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }

}
