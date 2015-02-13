package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.games.djc.utils.Button;

public class GameOverScreen extends ScreenAdapter {
    DjcGameManager gameManager;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    OrthographicCamera guiCam;

    Button GAMEOVER;
    Button points;
    Button contin;

    public GameOverScreen(long score, DjcGameManager gameManager, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        this.gameManager = gameManager;
        this.shapeRenderer = shapeRenderer;
        this.spriteBatch = spriteBatch;


        this.gameManager = gameManager;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        guiCam = new OrthographicCamera(Config.WIDTH, Config.HEIGHT);
        guiCam.position.set(Config.WIDTH / 2, Config.HEIGHT / 2, 0);
        guiCam.update();
        shapeRenderer.setAutoShapeType(true);
//        playBounds = Assets.font20.getBounds(playText);
//        playButton = new Rectangle(Config.WIDTH / 2 - playBounds.width / 2, 500, playBounds.width + 10, playBounds.height + 10);

        GAMEOVER = new Button("GAME OVER", new Rectangle(Config.WIDTH / 2 - 50, Config.HEIGHT / 2 + 100, 100f, 50f), spriteBatch, shapeRenderer);

        contin = new Button("Continue", new Rectangle(Config.WIDTH / 2 - 50, Config.HEIGHT / 2 - 100, 100f, 50f), spriteBatch, shapeRenderer);
        points = new Button(String.valueOf(score), new Rectangle(Config.WIDTH / 2 - 100, Config.HEIGHT / 2, 200f, 50f), spriteBatch, shapeRenderer);


    }


    public void update() {
        Vector3 touchPoint = new Vector3();
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (contin.getBounds().contains(touchPoint.x, touchPoint.y)) {
                gameManager.setScreen(new MainMenuScreen(gameManager, spriteBatch, shapeRenderer));
            }

        }

    }

    public void draw() {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(guiCam.combined);
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        GAMEOVER.draw(Color.RED);
        points.draw();
        contin.draw();

    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
