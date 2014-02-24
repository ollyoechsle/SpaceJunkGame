package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.util.Assets;

public class Ship extends Actor {

  protected TextureRegion texture;

  public Ship(Assets assets) {
    super();
    this.texture = new TextureRegion(assets.ship);
    this.setX(0);
    this.setY(0);
    this.setWidth(FallingJunk.SIZE * 3);
    this.setHeight(FallingJunk.SIZE * 3);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
  }

}
