package com.games.djc.Game.Logic;

public class Boost extends GameEntity {

    public enum BoostType {
        BOOST,
        BIG_BOOST
    }

    private final static float BIG_BOOST_SIZE_CHAGNGE = 2;

    private final static float WIDTH = 15;
    private final static float HEIGTH = 10;
    private final BoostType type;
    private final Platform onPlatform;


    public Boost(BoostType type, Platform platform) {
        super(platform.getPosition().x, platform.getPosition().y + 5 + Platform.PLATFORM_HEIGHT, WIDTH, HEIGTH);
        if (type == BoostType.BIG_BOOST) {
            bounds.set(bounds.x - BIG_BOOST_SIZE_CHAGNGE, bounds.y, bounds.width + 2 * BIG_BOOST_SIZE_CHAGNGE, bounds.height + 2 * BIG_BOOST_SIZE_CHAGNGE + BIG_BOOST_SIZE_CHAGNGE);
        }
        this.type = type;
        onPlatform = platform;
    }

    public void update() {
        if (onPlatform != null) {
            position.set(onPlatform.getPosition().x, onPlatform.getPosition().y + 5 + Platform.PLATFORM_HEIGHT);
            bounds.set(position.x - WIDTH / 2, position.y - HEIGTH / 2, WIDTH, HEIGTH);
            if (type == BoostType.BIG_BOOST) {
                bounds.set(bounds.x - BIG_BOOST_SIZE_CHAGNGE, bounds.y, bounds.width + 2 * BIG_BOOST_SIZE_CHAGNGE, bounds.height + 2 * BIG_BOOST_SIZE_CHAGNGE + BIG_BOOST_SIZE_CHAGNGE);
            }
        }
    }

    public BoostType getType() {
        return type;
    }
}
