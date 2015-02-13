package com.games.djc.Game.Logic;

import com.badlogic.gdx.math.Vector2;


public class DynamicGameEntity extends GameEntity {
    protected final Vector2 velocity;
    protected final Vector2 accel;

    public enum Direction {
        LEFT,
        RIGHT,
        NONE
    }

    public DynamicGameEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2(0f, 0f);
        accel = new Vector2(0f, 0f);
    }


}
