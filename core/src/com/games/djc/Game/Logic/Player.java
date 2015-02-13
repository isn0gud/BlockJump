package com.games.djc.Game.Logic;


import com.games.djc.Game.Logic.PlayerUtils.PlayerBoost;
import com.games.djc.Game.Logic.PlayerUtils.PlayerShield;

public class Player extends DynamicGameEntity {
    public static final float PLAYER_JUMP_VELOCITY = 30;
    //mb move acceleration
//    public static final float PLAYER_MOVE_VELOCITY = 20;
    public static final float PLAYER_WIDTH = 20f;
    public static final float PLAYER_HEIGHT = 20f;
    private static final float JUMP_SPEED = 50;
    private static final float JUMP_SPEED_SUPER = 90;
    private static final float MAX_VELOCITY_HORIZONTAL_SUPER = 5;
    private static final float MAX_VELOCITY_HORIZONTAL = 50;
    private static final float BOOST_DISTANCE = World.HEIGTH;
    private static final float BOOST_DISTANCE_BIG = World.HEIGTH * 3;


    //TODO DEBUG DELETE
    private static final float MAX_JUMP_HEIGHT = (float) (0.5 * World.gravity * ((-JUMP_SPEED / World.gravity) * (-JUMP_SPEED / World.gravity)) + JUMP_SPEED * (-JUMP_SPEED / World.gravity));


    public enum State {
        GROUND,
        JUMP,
        JUMP_SUPER,
        BOOST,
        BOOSTED,
        FALL
    }

    private static final float DIRECTION_CHANGE_THRESHOLD = 20;


    private Direction verticalDirection;
    private State state;
    private PlayerBoost boost;
    private PlayerShield playerShield;

    private float stateTime;
    private float lastHeight;


    public Player(float x, float y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = State.GROUND;
        verticalDirection = Direction.NONE;
        stateTime = 0;
        playerShield = new PlayerShield();
    }

    public void update(float deltaTime, float accelX, boolean superJump) {

        float userVelocityInput = (accelX  * deltaTime);
//        if (userVelocityInput > 5) {
//            userVelocityInput = 5;
//        } else if (userVelocityInput < -5) {
//            userVelocityInput = -5;
//
//        }

        float travledDistance = position.y - lastHeight;
        lastHeight = position.y;
        switch (state) {
            case GROUND:
                if (superJump) {
                    setVelocity(velocity.x + userVelocityInput, JUMP_SPEED_SUPER, MAX_VELOCITY_HORIZONTAL_SUPER);
                    state = State.JUMP_SUPER;
                } else {
                    setVelocity(velocity.x + userVelocityInput, JUMP_SPEED, MAX_VELOCITY_HORIZONTAL);
                    state = State.JUMP;
                }
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                stateTime = 0;
                break;
            case BOOST:
                setVelocity(0, JUMP_SPEED_SUPER, MAX_VELOCITY_HORIZONTAL);
                state = State.BOOSTED;
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                stateTime = 0;
                break;
            case JUMP_SUPER:
                addVelocity(userVelocityInput, World.gravity * deltaTime, MAX_VELOCITY_HORIZONTAL_SUPER);
                if (velocity.y < 0) {
                    state = State.FALL;
                }
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                stateTime += deltaTime;
                break;
            case JUMP:
                addVelocity(userVelocityInput, World.gravity * deltaTime, MAX_VELOCITY_HORIZONTAL);
                if (velocity.y < 0) {
                    state = State.FALL;
                }
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                stateTime += deltaTime;
                break;
            case BOOSTED:
                boost.update(travledDistance);
                addVelocity(userVelocityInput, 0, MAX_VELOCITY_HORIZONTAL);
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                if (boost.getBoostLeft() < 0) {
                    state = State.JUMP;
                }
                stateTime += deltaTime;
                break;
            case FALL:
                addVelocity(userVelocityInput, World.gravity * deltaTime, MAX_VELOCITY_HORIZONTAL);
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                stateTime += deltaTime;
                break;
        }
//        System.out.println(velocity);
//        System.out.println(position.x);

        if (position.x < 0) {
            position.x = World.WIDTH;
        } else if (position.x > World.WIDTH) {
            position.x = 0;
        }


        updateBounds();
        updateDirection();
        playerShield.update(deltaTime);
    }


    private void setVelocity(float x, float y, float limitX) {
        velocity.set(x, y);
        if (velocity.x > limitX) {
            velocity.set(limitX, velocity.y);
        }
        if (velocity.x < -limitX) {
            velocity.set(-limitX, velocity.y);
        }
    }

    private void addVelocity(float x, float y, float limitX) {
        velocity.add(x, y);
        setVelocity(velocity.x, velocity.y, limitX);
    }

    private void updateDirection() {
        if (velocity.x > DIRECTION_CHANGE_THRESHOLD) {
            verticalDirection = Direction.RIGHT;
        } else if (velocity.x < -DIRECTION_CHANGE_THRESHOLD) {
            verticalDirection = Direction.LEFT;
        } else {
            verticalDirection = Direction.NONE;
        }
    }

    private void updateBounds() {
        bounds.x = position.x - PLAYER_WIDTH / 2;
        bounds.y = position.y - PLAYER_WIDTH / 2;
    }

    public Direction getVerticalDirection() {
        return verticalDirection;
    }

    public State getState() {
        return state;
    }

    public void hit() {
        this.state = State.GROUND;
    }

    public boolean hitMonster() {
        if (isShielded() || state == State.FALL || state == State.BOOST) {
            //TODO handle if is shielded
            return false;
        } else {
            return true;
        }
    }

    public void boost(PlayerBoost.Type boostType) {
        state = State.BOOST;
        boost = new PlayerBoost(boostType);
    }


    public static float getMaxJumpHeight() {
        return MAX_JUMP_HEIGHT;
    }

    public void activateShield() {
        playerShield.activate();
    }

    public boolean isShielded() {
        return playerShield.isActive();
    }


}
