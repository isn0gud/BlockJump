package com.games.djc;


import java.util.Map;

public interface Highscore {

    public String getName();

    public void saveScore();

    public Map<String, Long> getScores();

}
