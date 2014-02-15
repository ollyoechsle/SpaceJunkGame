package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Magnet extends RectangleActor {

  private TextureRegion texture;

  public Magnet(Texture texture) {
    this.texture = new TextureRegion(texture);

    this.setWidth(384);
    this.setHeight(64);
    this.setY(Block.SIZE * 4);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, this.getX(), this.getY());
  }

  public void moveX(float delta) {
    this.setX(this.getX() + delta);
    checkBounds();
  }

  public void moveTo(float x) {
    this.setX(
        x - (this.getWidth() / 2)
    );
    checkBounds();
  }

  private void checkBounds() {
    if (this.getX() < 0) {
      this.setX(0);
    }
    if (this.getX() > 800 - getWidth()) {
      this.setX(800 - getWidth());
    }
  }


}
