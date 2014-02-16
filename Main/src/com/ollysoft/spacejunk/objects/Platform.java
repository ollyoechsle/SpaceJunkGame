package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;

import java.util.ArrayList;
import java.util.List;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends RectangleActor {

  protected TextureRegion texture;
  protected JunkStack[] stacks;

  public Platform(TextureRegion texture, int width) {
    this.texture = texture;

    this.stacks = new JunkStack[width];
    for (int i = 0; i < width; i++) {
      this.stacks[i] = new JunkStack(this, i);
      this.addActor(this.stacks[i]);
    }

    this.setWidth(FallingJunk.SIZE * width);
    this.setHeight(FallingJunk.SIZE);
    this.setY(FallingJunk.SIZE * 5);
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    for (JunkStack stack : stacks) {
      stack.draw(batch, parentAlpha);
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

  protected class JunkStack extends RectangleActor {

    private Platform p;
    private int deltaX;
    private List<BasicJunk> stack;

    private JunkStack(Platform p, int x) {
      this.p = p;
      this.deltaX = x * BasicJunk.SIZE;
      this.stack = new ArrayList<BasicJunk>();
    }

    public void addJunk(BasicJunk fallenJunk) {
      BasicJunk newJunk = new BasicJunk(fallenJunk.type, fallenJunk.texture);
      newJunk.setX(this.deltaX);
      newJunk.setY(stack.size() * BasicJunk.SIZE);
      stack.add(newJunk);
      addActor(newJunk);
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
      for (BasicJunk junk : stack) {
        junk.draw(batch, parentAlpha);
      }
    }

    public int size() {
      return stack.size();
    }

  }
}
