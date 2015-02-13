package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.games.djc.utils.Button;

public class OptionsScreen extends ScreenAdapter {

    DjcGameManager gameManager;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    OrthographicCamera guiCam;

    Button changeName;
    Button name;


    public OptionsScreen(DjcGameManager gameManager, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        this.gameManager = gameManager;
        this.shapeRenderer = shapeRenderer;
        this.spriteBatch = spriteBatch;


        guiCam = new OrthographicCamera(Config.WIDTH, Config.HEIGHT);
        guiCam.position.set(Config.WIDTH / 2, Config.HEIGHT / 2, 0);
        guiCam.update();
        shapeRenderer.setAutoShapeType(true);
//        playBounds = Assets.font20.getBounds(playText);
//        playButton = new Rectangle(Config.WIDTH / 2 - playBounds.width / 2, 500, playBounds.width + 10, playBounds.height + 10);
        changeName = new Button("Change Name", new Rectangle(Config.WIDTH / 2 - 100, Config.HEIGHT / 2, 200f, 50f), spriteBatch, shapeRenderer);
        name = new Button("...", new Rectangle(Config.WIDTH / 2 - 100, Config.HEIGHT / 2 + 100, 200f, 50f), spriteBatch, shapeRenderer);

    }


    public void update() {
        Vector3 touchPoint = new Vector3();
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (changeName.getBounds().contains(touchPoint.x, touchPoint.y)) {
                name.setText(Config.highscore.getName());
            }

        }

    }

    public void draw() {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        spriteBatch.setProjectionMatrix(guiCam.combined);
        changeName.draw();
        name.draw();

    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

}
