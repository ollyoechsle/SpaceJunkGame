package com.ollysoft.spacejunk.objects.junk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.objects.util.RectangleActor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class BasicJunk extends RectangleActor {

  public static int SIZE = 64;

  public final TextureRegion texture;
  public final JunkType type;

  public BasicJunk(JunkType type, TextureRegion texture) {
    super();
    this.type = type;
    this.texture = texture;
    this.setWidth(SIZE);
    this.setHeight(SIZE);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, this.getX(), this.getY());
  }

}
