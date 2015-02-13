package com.games.djc.utils;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.games.djc.Assets;

public class Button {

    String text;
    Rectangle bounds;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    public Button(String text, Rectangle bounds, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.text = text;
        this.bounds = bounds;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
    }

    public void draw() {
        batch.begin();
        Assets.font20.draw(batch, text, bounds.x + 5, bounds.y + 30);
        batch.end();
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();
    }

    public void draw(Color color) {
        batch.begin();
        Assets.font20.draw(batch, text, bounds.x + 5, bounds.y + 30);
        batch.end();
        shapeRenderer.begin();
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
