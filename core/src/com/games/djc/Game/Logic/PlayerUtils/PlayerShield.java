package com.games.djc.Game.Logic.PlayerUtils;

/**
 * Created by Pius on 12.02.2015.
 */
public class PlayerShield {
    public static final float SHIELD_MAX_DURATION = 60;
    private float shieldTimeLeft;
    private boolean active;

    public PlayerShield() {
        active = true;
        shieldTimeLeft = 0;
    }

    public void activate() {
        active = true;
        shieldTimeLeft = SHIELD_MAX_DURATION;

    }

    public void update(float deltaTime) {
        shieldTimeLeft -= deltaTime;
        if (shieldTimeLeft < 0) {
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }
}
