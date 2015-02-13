package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pius on 12.02.2015.
 */
public class HighscoresScreen extends ScreenAdapter {
    DjcGameManager game;
    OrthographicCamera guiCam;
    Rectangle backBounds;
    Vector3 touchPoint;
    String[] highScores;
    float xOffset = 0;

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;


    public HighscoresScreen(DjcGameManager game, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        this.game = game;

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        backBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector3();
        highScores = new String[5];
        for (int i = 0; i < 5; i++) {
            highScores[i] = i + 1 + ". " + Config.highscores[i];
            xOffset = Math.max(Assets.font30.getBounds(highScores[i]).width, xOffset);
        }
        xOffset = 160 - xOffset / 2 + Assets.font30.getSpaceWidth() / 2;
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenuScreen(game, new SpriteBatch(), new ShapeRenderer()));
                return;
            }
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();

        spriteBatch.setProjectionMatrix(guiCam.combined);
        spriteBatch.disableBlending();
        spriteBatch.begin();
//        spriteBatch.draw(Assets.backgroundRegion, 0, 0, 320, 480);
        spriteBatch.end();

        spriteBatch.enableBlending();
        spriteBatch.begin();
//        spriteBatch.draw( 10, 360 - 16, 300, 33);

        float y = 230;
        for (int i = 4; i >= 0; i--) {
            Assets.font30.draw(spriteBatch, highScores[i], xOffset, y);
            y += Assets.font30.getLineHeight();
        }

        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}