package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.util.RectangleGroup;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends RectangleGroup {

  protected JunkStack[] stacks;

  public Platform(TextureRegion texture, int width) {
    super();

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
    float dx = junk.getX() - this.getX();
    int stackIndex = (int) (dx / FallingJunk.SIZE);
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

