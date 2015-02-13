package com.games.djc.Game.Logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Predicate;
import com.games.djc.Config;
import com.games.djc.Game.Logic.PlayerUtils.PlayerBoost;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    public enum WorldState {
        GAME_OVER,
        GAME_RUNNING
    }

    private enum DifficultyStages {
        STATIC,
        MOVING,
        EXPLODING_PLATFORMS,
        MONSTERS
    }

    public static final int WIDTH = Config.WIDTH;
    public static final int HEIGTH = Config.HEIGHT;

    private static final int PREDEF_PLATFORMS = 20;
    private static final int MIN_PLATFORM_GAP = 50;

    private static final float speedup = 7;
    public static final float gravity = -10;
    private static float sideAccel = 0;
    private final Player player;
    private final List<Platform> platforms;
    private final List<Monster> monsters;
    private final List<Collectable> collectables;
    private final List<Boost> boosts;
    private long worldLevel;
    //level of highest created platform
    private long platformLevel;

    private long extraPoints;

    //TODO delete Debug
    private float maxJumpHeigth = 0;

    private Random random;

    private WorldState state;

    public World() {
        this.player = new Player(WIDTH / 2, 0);
        player.activateShield();
        worldLevel = 0;
        platformLevel = worldLevel;
        state = WorldState.GAME_RUNNING;
        platforms = new ArrayList<Platform>();
        monsters = new ArrayList<Monster>();
        collectables = new ArrayList<Collectable>();
        boosts = new ArrayList<Boost>();
        random = new Random();


    }

    public void update(float deltaTime, float accelX, boolean superTouched) {
        sideAccel = accelX;
        checkCollisions();
        player.update(deltaTime * speedup, accelX, superTouched);
        updateWorldLevel();

        updatePlatforms(deltaTime);
        updateBoosts();
        updateCollectables();
        updateMonster();

        if (platformLevel < worldLevel + 3 * HEIGTH) {

            addPlattforms();
            addCollectables();
            addMonsters();
        }
        checkGameOver();

    }
//    public static float getSideAccel() {
//        return sideAccel;
//    }


    private void updatePlatforms(float deltaTime) {
        for (Platform p : platforms) {
            p.update(deltaTime);
        }
        removeFromListIf(new Predicate<Platform>() {
            @Override
            public boolean evaluate(Platform platform) {
                return platform.getPosition().y < worldLevel - HEIGTH || platform.getState() == Platform.PlatformState.EXPLODED;
            }
        }, platforms);
    }


    private void addPlattforms() {
        for (int i = 0; i < PREDEF_PLATFORMS; i++) {
            platformLevel = platformLevel + (long) (MIN_PLATFORM_GAP + random.nextFloat() * (Player.getMaxJumpHeight() - MIN_PLATFORM_GAP));


            //add fake Plattform
            if (Math.abs(random.nextInt() % 10) < 2) {
                Platform plat = new Platform(Platform.PlatformType.FAKE, Platform.PLATFORM_WIDTH / 2 + random.nextFloat()
                        * (WIDTH - Platform.PLATFORM_WIDTH), platformLevel + (random.nextInt() % MIN_PLATFORM_GAP));
                platforms.add(plat);
            }

            Platform nextPlatform;
            switch (Math.abs(random.nextInt() % 5)) {
                case 1:
                    nextPlatform = new Platform(Platform.PlatformType.MOVING, Platform.PLATFORM_WIDTH / 2 + random.nextFloat()
                            * (WIDTH - Platform.PLATFORM_WIDTH), platformLevel);
                    break;
                case 2:
                    nextPlatform = new Platform(Platform.PlatformType.EXPLODING, Platform.PLATFORM_WIDTH / 2 + random.nextFloat()
                            * (WIDTH - Platform.PLATFORM_WIDTH), platformLevel);
                    break;
                default:
                    nextPlatform = new Platform(Platform.PlatformType.STAIC, Platform.PLATFORM_WIDTH / 2 + random.nextFloat()
                            * (WIDTH - Platform.PLATFORM_WIDTH), platformLevel);
            }
            platforms.add(nextPlatform);
            int rnd = Math.abs(random.nextInt() % 10);
            final int BOOST_DISTRIBUTION = 7;
            if (rnd > BOOST_DISTRIBUTION) {
                addBoostOnPlatform(nextPlatform);
            }
        }


    }

    private void updateMonster() {
        removeFromListIf(new Predicate<Monster>() {
            @Override
            public boolean evaluate(Monster monster) {
                return monster.getPosition().y < worldLevel - HEIGTH;
            }
        }, monsters);
    }

    private void addMonsters() {
        monsters.add(new Monster(random.nextFloat() * WIDTH, worldLevel + (random.nextFloat() * HEIGTH), 10, new Vector2(10, 0)));
    }

    private void updateCollectables() {
        removeFromListIf(new Predicate<Collectable>() {
            @Override
            public boolean evaluate(Collectable collectable) {
                return collectable.getPosition().y < worldLevel - HEIGTH || collectable.isCollected();
            }
        }, collectables);
    }

    private void addCollectables() {
        for (int i = 0; i < 2; i++) {
            int rnd = Math.abs(random.nextInt() % 10);
            final int COLLECTABLE_DISTRIBUTION = 5;
            if (rnd < COLLECTABLE_DISTRIBUTION) {
                collectables.add(new Collectable(random.nextFloat() * WIDTH, worldLevel + (random.nextFloat() * HEIGTH), Collectable.Type.BONUS_POINTS));
            } else {
                collectables.add(new Collectable(random.nextFloat() * WIDTH, worldLevel + (random.nextFloat() * HEIGTH), Collectable.Type.SHIELD));
            }
        }
    }

    private void addBoostOnPlatform(Platform platform) {
        if (platform.getType() != Platform.PlatformType.EXPLODING) {
            int rnd = Math.abs(random.nextInt() % 10);
            final int BOOST_DISTRIBUTION = 8;
            if (rnd < BOOST_DISTRIBUTION) {
                boosts.add(new Boost(Boost.BoostType.BOOST, platform));
            } else {
                boosts.add(new Boost(Boost.BoostType.BIG_BOOST, platform));
            }
        }
    }

    private void updateBoosts() {
        for (Boost b : boosts) {
            b.update();
        }
        removeFromListIf(new Predicate<Boost>() {
            @Override
            public boolean evaluate(Boost boost) {
                return boost.getPosition().y < worldLevel - HEIGTH;
            }
        }, boosts);
    }


    private void checkCollisions() {
        checkMonsterCollisions();
        checkPlatformCollisions();
        checkCollectableCollisions();
        checkBoostsCollisions();

    }

    private void checkCollectableCollisions() {
        for (Collectable c : collectables) {
            if (!c.isCollected() && player.getBounds().overlaps(c.getBounds())) {
                switch (c.getType()) {
                    case BONUS_POINTS:
                        extraPoints += Collectable.BONUS_POINTS_AMOUNT;
                        break;
                    case SHIELD:
                        player.activateShield();
                        break;
                }
                c.collect();

            }
        }
    }

    private void checkPlatformCollisions() {
        if (player.getPosition().y < 0) {
            player.hit();
            return;
        }
        if (player.getState() == Player.State.FALL) {
            for (Platform p : platforms) {
                if (p.getType() != Platform.PlatformType.FAKE && player.bounds.overlaps(p.bounds)) {
                    player.hit();
                    p.explode();
                    return;
                }
            }
        }
    }

    private void checkMonsterCollisions() {
        for (Monster m : monsters) {
            if (player.bounds.overlaps(m.bounds)) {
                if (player.hitMonster()) {
                    state = WorldState.GAME_OVER;
                } else {
                    m.destroy();
                }
                return;
            }
        }
    }

    private void checkBoostsCollisions() {
        if (player.getState() == Player.State.FALL) {
            for (Boost b : boosts) {
                if (player.bounds.overlaps(b.bounds)) {
                    switch (b.getType()) {
                        case BIG_BOOST:
                            player.boost(PlayerBoost.Type.BIG);
                            break;
                        case BOOST:
                            player.boost(PlayerBoost.Type.NORMAL);
                            break;
                    }
                    return;
                }
            }
        }
    }

    private void updateWorldLevel() {
        if (player.position.y > worldLevel) {
            worldLevel = (long) player.position.y;
        }
    }

    private void checkGameOver() {
        if (player.position.y < worldLevel - HEIGTH / 2)
            state = WorldState.GAME_OVER;

    }

    private void removeFromListIf(Predicate p, List l) {
        for (int i = 0; i < l.size(); i++) {
            if (p.evaluate(l.get(i))) {
                l.remove(i);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public WorldState getState() {
        return state;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Boost> getBoosts() {
        return boosts;
    }

    public List<Collectable> getCollectables() {
        return collectables;
    }

    public long getPoints() {
        return extraPoints + (worldLevel / HEIGTH);
    }
}
