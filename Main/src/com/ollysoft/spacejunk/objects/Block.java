package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Block extends Actor {

  private final TextureRegion texture;

  public Block(Texture texture) {
    this.texture = new TextureRegion(texture);

    this.setWidth(64);
    this.setHeight(64);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, this.getX(), this.getY());
  }

  @Override
  public void act(float delta) {
    float y = getY() - (200 * delta);
    if (y + 64 < 0) {
      remove();
    } else {
      setY(y);
    }
    //if (obj.overlaps(magnet)) {
    //  dropSound.play();
    //  iter.remove();
    //}
  }
}
