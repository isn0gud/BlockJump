package com.games.djc.Game.Logic;

/**
 * Created by Pius on 11.02.2015.
 */
public class Collectable extends GameEntity {

    public enum Type {
        SHIELD,
        BONUS_POINTS
    }

    public final static int BONUS_POINTS_AMOUNT = 5;
    private final static float WIDTH = 20;
    private final static float HEIGTH = 20;
    private final Type type;

    private boolean collected = false;


    public Collectable(float x, float y, Type type) {
        super(x, y, WIDTH, HEIGTH);
        this.type = type;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public Type getType() {
        return type;
    }
}
