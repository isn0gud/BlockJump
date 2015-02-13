package com.games.djc.desktop;

import com.games.djc.Highscore;

import java.util.Map;

/**
 * Created by Pius on 13.02.2015.
 */
public class HighscoreImpl implements Highscore {
    @Override
    public String getName() {
        return "hans";
    }

    @Override
    public void saveScore() {

    }

    @Override
    public Map<String, Long> getScores() {
        return null;
    }
}
