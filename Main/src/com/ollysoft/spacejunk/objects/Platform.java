package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.objects.util.RectangleGroup;

import java.util.Iterator;

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

  protected class JunkStack extends Group {

    private final Platform platform;
    private final int x;
    private int deltaX;

    private JunkStack(Platform platform, int x) {
      this.platform = platform;
      this.x = x;
      this.deltaX = x * BasicJunk.SIZE;
      this.setTransform(false);
    }

    public boolean addJunk(BasicJunk fallenJunk) {
      BasicJunk newJunk = new BasicJunk(fallenJunk.type, fallenJunk.texture);
      newJunk.setX(this.deltaX);
      newJunk.setY((size() + 1) * BasicJunk.SIZE);
      addActor(newJunk);

      checkedAlready.clear();
      sameType.clear();
      int y = getChildren().indexOf(newJunk, true);
      findBlocksOfSameType(x, y, newJunk.type);

      if (sameType.size >= 3) {
        hideBlocks();
        return true;
      }

      return false;
    }

    private void hideBlocks() {
      Iterator<BasicJunk> iterator = sameType.iterator();
      while (iterator.hasNext()) {
        BasicJunk next = iterator.next();
        next.addAction(sequence(
            Actions.alpha(0f, 2),
            Actions.removeActor()
        ));
      }
    }

    private Array<BasicJunk> sameType = new Array<BasicJunk>();
    private Array<BasicJunk> checkedAlready = new Array<BasicJunk>();

    private void findBlocksOfSameType(int x, int y, JunkType expectedType) {

      BasicJunk newJunk = platform.junkAt(x, y);

      if (newJunk == null || newJunk.type != expectedType) {
        return;
      }

      if (checkedAlready.contains(newJunk, true)) {
        return;
      } else {
        checkedAlready.add(newJunk);
      }

      if (sameType.contains(newJunk, true)) {
        return;
      } else {
        sameType.add(newJunk);
      }

      findBlocksOfSameType(x - 1, y, expectedType); // left
      findBlocksOfSameType(x + 1, y, expectedType); // right
      findBlocksOfSameType(x, y - 1, expectedType); // down

    }

    public BasicJunk junkAt(int y) {
      SnapshotArray<Actor> children = getChildren();
      if (y >= children.size) {
        return null;
      }

      if (y < 0) {
        return null;
      }

      return (BasicJunk) children.get(y);
    }

    public int size() {
      return getChildren().size;
    }

  }

}

