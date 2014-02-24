package com.ollysoft.spacejunk.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

  public final TextureRegion plainRock;
  public final TextureRegion redRock;
  public final TextureRegion greenRock;
  public final TextureRegion goldRock;
  public final Texture starsBackground;
  public final Skin uiSkin;

  public final BitmapFont bigFont;

  public final Texture rocksTexture;
  public final Texture ship;
  public final Texture boulder;
  public final Sound dropSound;
  public final Sound scoreSound;
  public final Sound crashSound;
  public final Music music;

  public Assets() {
    rocksTexture = new Texture(Gdx.files.internal("rocks.png"));
    plainRock = new TextureRegion(rocksTexture, 0, 0, 64, 64);
    redRock = new TextureRegion(rocksTexture, 64, 0, 64, 64);
    greenRock = new TextureRegion(rocksTexture, 128, 0, 64, 64);
    goldRock = new TextureRegion(rocksTexture, 196, 0, 64, 64);

    ship = new Texture(Gdx.files.internal("ship.png"));
    boulder = new Texture(Gdx.files.internal("boulder.png"));

    dropSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
    scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
    crashSound = Gdx.audio.newSound(Gdx.files.internal("crash.wav"));
    music = Gdx.audio.newMusic(Gdx.files.internal("music-2.mp3"));
    music.setLooping(true);

    uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

    starsBackground = new Texture(Gdx.files.internal("stars_background.png"));

    bigFont = new BitmapFont(Gdx.files.internal("acknowledge.fnt"));
  }

  public void dispose() {
    rocksTexture.dispose();
    bigFont.dispose();
    starsBackground.dispose();
    boulder.dispose();

    ship.dispose();
    dropSound.dispose();
    crashSound.dispose();
    music.dispose();
  }

}
