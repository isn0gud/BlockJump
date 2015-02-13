package com.games.djc.Game.Logic;

import com.badlogic.gdx.math.Vector2;

public class Monster extends DynamicGameEntity {

    private Direction verticalDirection;
    private float stateTime;
    private final static float WIDTH = 50;
    private final static float HEIGTH = 40;

    private final float startX;
    private final float endX;


    public Monster(float x, float y, float movingDistance, Vector2 velocity) {
        super(x, y, WIDTH, HEIGTH);
        startX = x;
        endX = x + movingDistance;
        this.velocity.set(velocity);
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        if (position.x > endX || position.x < startX) {
            velocity.set(-velocity.x, 0);
        }

        updateBounds();

    }

    public void destroy(){

    }

    private void updateBounds() {
        bounds.x = position.x - WIDTH / 2;
        bounds.y = position.y - HEIGTH / 2;
    }
}
