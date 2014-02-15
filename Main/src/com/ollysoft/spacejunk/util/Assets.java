package com.ollysoft.spacejunk.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

  public final TextureRegion rock1;
  public final TextureRegion rock2;

  private final Texture texture;

  public Assets() {
    texture = new Texture(Gdx.files.internal("rocks.png"));
    rock1 = new TextureRegion(texture, 0, 0, 64, 64);
    rock2 = new TextureRegion(texture, 64, 0, 64, 64);
  }

  public void dispose() {
    texture.dispose();
  }

}
