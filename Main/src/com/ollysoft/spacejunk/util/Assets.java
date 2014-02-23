package com.ollysoft.spacejunk.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

  public final TextureRegion plainRock;
  public final TextureRegion redRock;
  public final TextureRegion greenRock;
  public final TextureRegion goldRock;
  public final Texture starsBackground;

  public final BitmapFont bigFont;

  private final Texture texture;

  public Assets() {
    texture = new Texture(Gdx.files.internal("rocks.png"));
    plainRock = new TextureRegion(texture, 0, 0, 64, 64);
    redRock = new TextureRegion(texture, 64, 0, 64, 64);
    greenRock = new TextureRegion(texture, 128, 0, 64, 64);
    goldRock = new TextureRegion(texture, 196, 0, 64, 64);

    starsBackground = new Texture(Gdx.files.internal("stars_background.png"));

    bigFont = new BitmapFont(Gdx.files.internal("acknowledge.fnt"));
  }

  public void dispose() {
    texture.dispose();
    bigFont.dispose();
    starsBackground.dispose();
  }

}
