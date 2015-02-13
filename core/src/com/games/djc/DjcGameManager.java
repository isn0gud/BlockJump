package com.games.djc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DjcGameManager extends Game {

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;

    public DjcGameManager(Highscore hs) {
        Config.highscore = hs;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        Assets.load();
        setScreen(new MainMenuScreen(this, spriteBatch, shapeRenderer));
    }

    @Override
    public void render() {
        super.render();
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
    }
}
