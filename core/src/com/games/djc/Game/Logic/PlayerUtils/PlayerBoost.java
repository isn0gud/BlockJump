package com.games.djc.Game.Logic.PlayerUtils;

import com.games.djc.Game.Logic.World;

/**
 * Created by Pius on 12.02.2015.
 */
public class PlayerBoost {

    private static final float BIG_BOOST_SIZE = 3 * World.HEIGTH;
    private static final float NORMAL_BOOST_SIZE = World.HEIGTH;

    public enum Type {
        BIG,
        NORMAL
    }

    private float boostLeft;

    public PlayerBoost(Type type) {
        switch (type) {
            case BIG:
                boostLeft = BIG_BOOST_SIZE;
                break;
            case NORMAL:
                boostLeft = NORMAL_BOOST_SIZE;
                break;
        }
    }

    public boolean update(float travledDistance) {
        boostLeft -= travledDistance;
        if (boostLeft < 0) {
            return true;
        }
        return false;
    }

    public float getBoostLeft() {
        return boostLeft;
    }
}
