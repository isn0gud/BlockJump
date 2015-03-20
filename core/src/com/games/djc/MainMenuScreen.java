package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.games.djc.Game.GameScreen;
import com.games.djc.utils.Button;


/**
 * Created by Pius on 17.01.2015.
 */
public class MainMenuScreen extends ScreenAdapter {

    DjcGameManager gameManager;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    OrthographicCamera guiCam;

    Button playButton;
//    Button optionsButton;
//    Button highscoreButton;


    public MainMenuScreen(DjcGameManager gameManager, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.gameManager = gameManager;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        guiCam = new OrthographicCamera(Config.WIDTH, Config.HEIGHT);
        guiCam.position.set(Config.WIDTH / 2, Config.HEIGHT / 2, 0);
        guiCam.update();
        shapeRenderer.setAutoShapeType(true);

        playButton = new Button("Play", new Rectangle(Config.WIDTH / 2 - 50, Config.HEIGHT / 2 + 100, 100f, 50f), spriteBatch, shapeRenderer);
//        optionsButton = new Button("Options", new Rectangle(Config.WIDTH / 2 - 50, Config.HEIGHT / 2, 100f, 50f), spriteBatch, shapeRenderer);
//        highscoreButton = new Button("Highscore", new Rectangle(Config.WIDTH / 2 - 50, Config.HEIGHT / 2 - 100, 100f, 50f), spriteBatch, shapeRenderer);

    }

    public void update() {
        Vector3 touchPoint = new Vector3();
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (playButton.getBounds().contains(touchPoint.x, touchPoint.y)) {
                gameManager.setScreen(new GameScreen(gameManager, spriteBatch, shapeRenderer));
//            } else if (optionsButton.getBounds().contains(touchPoint.x, touchPoint.y)) {
//                gameManager.setScreen(new OptionsScreen(gameManager, shapeRenderer, spriteBatch));
//            } else if (highscoreButton.getBounds().contains(touchPoint.x, touchPoint.y)) {
//                gameManager.setScreen(new HighscoresScreen(gameManager, spriteBatch, shapeRenderer));
            }
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        spriteBatch.setProjectionMatrix(guiCam.combined);
        playButton.draw();
//        optionsButton.draw();
//        highscoreButton.draw();

    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
