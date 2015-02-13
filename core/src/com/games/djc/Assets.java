package com.games.djc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Pius on 17.01.2015.
 */
public class Assets {

    public static Texture background;
    //    public static TextureRegion backgroundRegion;
//    public static Texture test;
//    public static TextureRegion testRegion;
    public static Texture mixedAssets;
    public static TextureRegion platform;
    public static Animation playerJump;
    public static Animation playerFall;
    public static BitmapFont font20;
    public static BitmapFont font30;


    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/UbuntuMono-R.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font20 = generator.generateFont(parameter); // font size 12 pixels
        parameter.size = 30;
        font30 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        background = loadTexture("background.png");
//        backgroundRegion = new TextureRegion(background, 0, 0, Config.WIDTH, Config.HEIGHT);
//        test = loadTexture("test1.png");
//        testRegion = new TextureRegion(background, 100, 100, 50, 50);
        mixedAssets = loadTexture("mixedAssets.png");
        playerJump = new Animation(1f, new TextureRegion(mixedAssets, 0, 0, 36, 36), new TextureRegion(mixedAssets, 36, 0, 36, 36), new TextureRegion(mixedAssets, 72, 0, 36, 36));
        playerFall = new Animation(0.2f, new TextureRegion(mixedAssets, 72, 0, 36, 36), new TextureRegion(mixedAssets, 108, 0, 36, 36), new TextureRegion(mixedAssets, 0, 0, 36, 36));
        platform = new TextureRegion(mixedAssets, 144, 0, 117, 12);

    }
}
