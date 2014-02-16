package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends Group {

  protected final JunkStack[] stacks;
  protected final GameScreen game;
  private final Rectangle temporaryRectangle = new Rectangle();

  public Platform(TextureRegion texture, int width, GameScreen game) {
    super();
    this.game = game;

    this.setTransform(false);

    this.setWidth(FallingJunk.SIZE * width);
    this.setHeight(FallingJunk.SIZE);

    this.setX(GameScreen.width - (this.getWidth() / 2));
    this.setY(FallingJunk.SIZE * 5);

    addActor(new Paddle(texture, width));

    this.stacks = new JunkStack[width];
    for (int x = 0; x < width; x++) {
      this.stacks[x] = new JunkStack(this, x);
      addActor(this.stacks[x]);
    }

  }

  public int overlaps(Rectangle other) {
    // move the rectangle into the coordinate space of the platform
    float width = (other.getWidth() - 1) / 2;
    temporaryRectangle.setX(other.getX() - this.getX() + width);
    temporaryRectangle.setY(other.getY() - this.getY());
    temporaryRectangle.setWidth(1);
    temporaryRectangle.setHeight(other.getHeight());
    for (int i = 0; i < stacks.length; i++) {
      if (stacks[i].rectangle.overlaps(temporaryRectangle)) {
        if (temporaryRectangle.y < stacks[i].rectangle.height - (BasicJunk.SIZE / 2)) {
          // already passed the top of the stack
          continue;
        }
        return i;
      }
    }
    return -1;
  }

  public void repositionAllRocks() {
    for (int i = 0; i < stacks.length; i++) {
      stacks[i].repositionRocks();
    }
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
    if (this.getX() > GameScreen.width - getWidth()) {
      this.setX(GameScreen.width - getWidth());
    }
  }

  public boolean addJunk(BasicJunk junk) {
    return addJunk(junk, overlaps(junk.getRectangle()));
  }

  public boolean addJunk(BasicJunk junk, int stackIndex) {
    return stacks[stackIndex].addJunk(junk);
  }

  public BasicJunk junkAt(int x, int y) {

    if (x >= stacks.length) {
      return null;
    }

    if (x < 0) {
      return null;
    }

    return stacks[x].junkAt(y);

  }

}

