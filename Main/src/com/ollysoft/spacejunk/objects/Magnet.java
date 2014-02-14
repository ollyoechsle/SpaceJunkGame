package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Magnet extends Actor {

  private TextureRegion texture;

  public Magnet(Texture texture) {
    this.texture = new TextureRegion(texture);

    this.setWidth(64);
    this.setHeight(64);
  }

  public void moveX(float delta) {
    this.setX(this.getX() + delta);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    /*public void draw(TextureRegion region,
            float x,
            float y,
            float originX,
            float originY,
            float width,
            float height,
            float scaleX,
            float scaleY,
            float rotation)*/
    //batch
    //.draw(texture, this.x, this.y, this.width / 2, this.height / 2, this.width, this.height, 1f,
    //      1f, rotation);
    batch.draw(texture, this.getX(), this.getY());
  }
}
