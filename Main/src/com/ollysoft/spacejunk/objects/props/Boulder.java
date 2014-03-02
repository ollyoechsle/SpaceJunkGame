package com.ollysoft.spacejunk.objects.props;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

public class Boulder extends Actor {

  private TextureRegion texture;

  public Boulder(Assets assets, float x, float y, float size) {
    this.texture = new TextureRegion(assets.boulder);
    this.setWidth(size);
    this.setHeight(size);
    this.setX(x);
    this.setY(y);
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY());
  }
}
