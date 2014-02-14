package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Magnet extends Rectangle {

  private TextureRegion texture;
  private float rotation = 0f;

  public Magnet(Texture texture) {
    this.texture = new TextureRegion(texture);
    this.width = 64;
    this.height = 64;
  }

  public void draw(SpriteBatch batch) {
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
    batch
        .draw(texture, this.x, this.y, this.width / 2, this.height / 2, this.width, this.height, 1f,
              1f, rotation);
  }
}
