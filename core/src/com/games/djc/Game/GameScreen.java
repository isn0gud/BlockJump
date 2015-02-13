package com.games.djc.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.games.djc.DjcGameManager;
import com.games.djc.Game.Display.WorldRenderer;
import com.games.djc.Game.Logic.World;
import com.games.djc.GameOverScreen;

/**
 * Created by Pius on 20.01.2015.
 */
public class GameScreen extends ScreenAdapter {

    DjcGameManager gameManager;
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    //    OrthographicCamera camera;
    WorldRenderer worldRenderer;
    World world;

    private static final float keyPower = 20;
    private static final float periphalPower = 5;


    public GameScreen(DjcGameManager gameManager, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.gameManager = gameManager;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        world = new World();
        worldRenderer = new WorldRenderer(world, spriteBatch, shapeRenderer);
//        camera = new OrthographicCamera(Config.WIDTH / 2, Config.HEIGHT / 2);
//        camera.position.set(Config.WIDTH / 4, Config.HEIGHT / 4, 0);

    }

    public void update(float deltaTime) {

//        if (Gdx.input.justTouched() && !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//            gameManager.setScreen(new MainMenuScreen(gameManager, spriteBatch, shapeRenderer));
//        }

        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            System.out.println(Gdx.input.getAccelerometerX());
            world.update(deltaTime, -Gdx.input.getAccelerometerX() * 5, Gdx.input.isTouched(1));
            System.out.println(Gdx.input.getAccelerometerY());
        } else {
            float accel = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accel = -keyPower;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accel = keyPower;
            world.update(deltaTime, accel, Gdx.input.isButtonPressed(Input.Buttons.RIGHT));
        }
        if (world.getState() == World.WorldState.GAME_OVER) {
            gameManager.setScreen(new GameOverScreen(world.getPoints(), gameManager, shapeRenderer, spriteBatch));
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;

        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();


//
//        camera.update();
//        spriteBatch.setProjectionMatrix(camera.combined);
//        spriteBatch.enableBlending();
//        spriteBatch.begin();
//        spriteBatch.draw(Assets.background, 0, 0, Config.WIDTH, Config.HEIGHT);
//
//
//        spriteBatch.draw(Assets., Player., 10f);
//        spriteBatch.draw(Assets.playerJump, 20f, 20f);
//
//
//        spriteBatch.end();


    }


    @Override
    public void render(float delta) {
        update(delta);
        draw();

    }

    @Override
    public void pause() {
        super.pause();
    }
}
