package com.games.djc.Game.Logic;

/**
 * Created by Pius on 24.01.2015.
 */


public class Platform extends DynamicGameEntity {
    public enum PlatformType {
        MOVING,
        FAKE,
        STAIC,
        EXPLODING
    }

    public enum PlatformState {
        NORMAL,
        EXPLODING,
        EXPLODED
    }

    public static final float EXPLODING_TIME = 1;

    public static final float PLATFORM_WIDTH = 80f;
    public static final float PLATFORM_HEIGHT = 5f;
    public static final float PLATFORM_VELOCITY = 50;
    public static final float PLATFORM_ECPLODING_TIME = 0.2f * 4;

    private final PlatformType type;

    private float timeToLive;
    private PlatformState state;

    public Platform(PlatformType type, float x, float y) {
        super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        this.type = type;
        this.state = PlatformState.NORMAL;
        timeToLive = EXPLODING_TIME;
        if (type == PlatformType.MOVING) {
            velocity.x = PLATFORM_VELOCITY;
        }
    }

    public void update(float deltaTime) {
        switch (type) {
            case MOVING:
                position.add(velocity.x * deltaTime, 0);
                if (position.x < PLATFORM_WIDTH / 2) {
                    velocity.x = -velocity.x;
                    position.x = PLATFORM_WIDTH / 2;
                }
                if (position.x > World.WIDTH - PLATFORM_WIDTH / 2) {
                    velocity.x = -velocity.x;
                    position.x = World.WIDTH - PLATFORM_WIDTH / 2;
                }
                bounds.x = position.x - PLATFORM_WIDTH / 2;
                bounds.y = position.y - PLATFORM_HEIGHT / 2;
                break;
            case EXPLODING:
                if (state == PlatformState.EXPLODING) {
                    timeToLive -= deltaTime;
                }
                if (timeToLive < 0) {
                    state = PlatformState.EXPLODED;
                }
                break;
            default:
        }


    }

    public void explode() {
        if (type == PlatformType.EXPLODING) {
            state = PlatformState.EXPLODING;
        }
    }

    public PlatformState getState() {
        return state;
    }

    public PlatformType getType() {
        return type;
    }
}
