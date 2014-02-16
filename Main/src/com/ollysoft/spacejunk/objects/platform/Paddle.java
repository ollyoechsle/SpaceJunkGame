package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Paddle extends Actor {

  protected TextureRegion texture;

  public Paddle(TextureRegion texture, int width) {
    super();
    this.texture = texture;
    this.setWidth(FallingJunk.SIZE * width);
    this.setHeight(FallingJunk.SIZE);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
  }

}
