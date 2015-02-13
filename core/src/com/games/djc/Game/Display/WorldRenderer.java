package com.games.djc.Game.Display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.games.djc.Assets;
import com.games.djc.Config;
import com.games.djc.Game.Logic.*;

import java.util.Random;

public class WorldRenderer {

    private static final float PLAYER_ROTATE_DEGREE = 10;

    private World world;
    private OrthographicCamera worldCam;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    public WorldRenderer(World world, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
        worldCam = new OrthographicCamera(Config.WIDTH, Config.HEIGHT);
        worldCam.position.set(Config.WIDTH / 2f, Config.HEIGHT / 2f, 0);
        this.world = world;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;

    }

    public void render() {
        if (world.getPlayer().getPosition().y > worldCam.position.y)
            worldCam.position.y = world.getPlayer().getPosition().y;
        worldCam.update();
        shapeRenderer.setProjectionMatrix(worldCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        renderPlatforms();
        renderBoosts();
        renderCollectables();
        renderMonsters();
        renderPlayer();
        shapeRenderer.end();
        renderPoints();

//        spriteBatch.setProjectionMatrix(worldCam.combined);
//        spriteBatch.setProjectionMatrix(worldCam.combined);
//        spriteBatch.begin();
////        spriteBatch.setColor(Color.WHITE);
//        String points = String.valueOf("hans 123");
////        System.out.println(points);
////        BitmapFont.TextBounds tBounds = font.getBounds(points);
////        font.drawMultiLine(spriteBatch, points, 100, 100);
////        font.drawWrapped(spriteBatch, points, 100, 200, 0.5f);
//        font.draw(spriteBatch, points, 0, 100);
//        spriteBatch.end();

//        spriteBatch.setProjectionMatrix(worldCam.combined);
//        spriteBatch.begin();
//        spriteBatch.disableBlending();
//        spriteBatch.draw(Assets.background, Config.WIDTH, Config.HEIGHT);
//        spriteBatch.enableBlending();
//        renderPlatforms();
//        renderPlayer();
//        spriteBatch.end();
    }

    private void renderPlayer() {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        Player player = world.getPlayer();
        Rectangle bounds = player.getBounds();
        shapeRenderer.setColor(Color.GREEN);
        float rotateDegrees = 0;
        switch (player.getVerticalDirection()) {
            case LEFT:
                rotateDegrees = PLAYER_ROTATE_DEGREE;
                break;
            case RIGHT:
                rotateDegrees = -PLAYER_ROTATE_DEGREE;
                break;
        }
        shapeRenderer.rect(bounds.x, bounds.y, 0, 0, bounds.width, bounds.height, 1, 1, rotateDegrees);
        if (world.getPlayer().isShielded()) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(player.getPosition().x, player.getPosition().y, bounds.width / 1.3f);
        }
    }

    //    private void renderPlayer() {
//
//        TextureRegion keyFrame;
//        switch (world.getPlayer().getState()) {
////            case JUMP:
////                keyFrame = Assets.playerJump.getKeyFrame(world.getPlayer().getStateTime(), true);
////                break;
//            default:
//                keyFrame = Assets.playerJump.getKeyFrame(0);
//        }
//        spriteBatch.draw(keyFrame, world.getPlayer().getPosition().x, world.getPlayer().getPosition().y);
//
//
//    }
//

    private void renderMonsters() {
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);

        Random rnd = new Random();
        shapeRenderer.setColor(Color.CYAN);
        for (Monster m : world.getMonsters()) {
            float[] vertices = new float[12];
            for (int i = 0; i < 12; i = i + 2) {
                vertices[i] = rnd.nextFloat() * m.getBounds().width + m.getBounds().x;
                vertices[i + 1] = (rnd.nextFloat() * m.getBounds().height) + m.getBounds().y;
            }
            shapeRenderer.polygon(vertices);
        }
    }


    private void renderPlatforms() {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        for (Platform p : world.getPlatforms()) {
            Rectangle bounds = p.getBounds();

            switch (p.getType()) {
                case EXPLODING:
                    switch (p.getState()) {
                        case EXPLODED:
                            break;
                        case EXPLODING:
                            shapeRenderer.setColor(Color.RED);
                            break;
                        case NORMAL:
                            shapeRenderer.setColor(Color.ORANGE);

                            break;
                    }
                    break;
                case MOVING:
                    shapeRenderer.setColor(Color.BLUE);

                    break;
                case STAIC:
                    shapeRenderer.setColor(Color.BLACK);

                    break;
                case FAKE:
                    shapeRenderer.setColor(new Color(0.38f, 0.2f, 0.07f, 1f));
            }
            shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void renderBoosts() {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (Boost b : world.getBoosts()) {
            Rectangle bounds = b.getBounds();
//            shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
            switch (b.getType()) {
                case BOOST:
                    shapeRenderer.setColor(Color.MAGENTA);
                    break;
                case BIG_BOOST:
                    shapeRenderer.setColor(new Color(255, 215, 0, 0));

                    break;
            }
            shapeRenderer.triangle(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y, bounds.x + bounds.width / 2, bounds.y + bounds.height);

        }
    }

    private void renderCollectables() {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (Collectable c : world.getCollectables()) {
            Rectangle bounds = c.getBounds();
            if (!c.isCollected()) {
                switch (c.getType()) {
                    case BONUS_POINTS:
                        shapeRenderer.setColor(Color.YELLOW);
                        shapeRenderer.circle(c.getPosition().x, c.getPosition().y, bounds.width / 2);
                        break;
                    case SHIELD:
                        shapeRenderer.setColor(Color.RED);
                        shapeRenderer.circle(c.getPosition().x, c.getPosition().y, bounds.width / 2);
                        shapeRenderer.setColor(Color.LIGHT_GRAY);
                        shapeRenderer.circle(c.getPosition().x, c.getPosition().y, bounds.width / 2 - 2);
                        break;
                }
            }
        }
    }

    private void renderPoints() {
        spriteBatch.begin();
        String points = String.valueOf(world.getPoints());
        BitmapFont.TextBounds bounds = Assets.font20.getBounds(points);
        Assets.font20.draw(spriteBatch, points, Config.WIDTH - bounds.width, Config.HEIGHT - bounds.height);
        spriteBatch.end();
    }

}
