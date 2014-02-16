package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.util.RectangleGroup;

import java.util.ArrayList;
import java.util.List;

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
      this.stacks[x] = new JunkStack(x);
      addActor(this.stacks[x]);
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

  public void addJunk(BasicJunk junk) {
    float dx = junk.getX() - this.getX();
    int stackIndex = (int) (dx / FallingJunk.SIZE);
    stacks[stackIndex].addJunk(junk);
  }

  protected class JunkStack extends Group {

    private int deltaX;
    private List<BasicJunk> stack;

    private JunkStack(int x) {
      this.deltaX = x * BasicJunk.SIZE;
      this.stack = new ArrayList<BasicJunk>();
      this.setTransform(false);
    }

    public void addJunk(BasicJunk fallenJunk) {
      BasicJunk newJunk = new BasicJunk(fallenJunk.type, fallenJunk.texture);
      newJunk.setX(this.deltaX);
      newJunk.setY((stack.size() + 1) * BasicJunk.SIZE);
      stack.add(newJunk);
      addActor(newJunk);
    }

    public int size() {
      return stack.size();
    }

  }
}
