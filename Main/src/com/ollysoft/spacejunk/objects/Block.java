package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.GameScreen;

import static com.badlogic.gdx.math.Intersector.overlaps;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Block extends RectangleActor {

  public static int SIZE = 64;

  private final TextureRegion texture;
  private final GameScreen game;

  public Block(Texture texture, GameScreen game) {
    super();
    this.game = game;
    this.texture = new TextureRegion(texture);
    this.setWidth(SIZE);
    this.setHeight(SIZE);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, this.getX(), this.getY());
  }

  @Override
  public void act(float delta) {
    float y = getY() - (200 * delta);
    if (y + SIZE < 0) {
      game.crashSound.play();
      game.score.onMissedBlock(this);
      remove();
    } else {
      setY(y);
    }
    if (overlaps(getRectangle(), game.magnet.getRectangle())) {
      game.dropSound.play();
      game.score.onCollectedBlock(this);
      this.remove();
    }
  }

}
