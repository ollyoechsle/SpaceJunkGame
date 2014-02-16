package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;

import java.util.Iterator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * com.ollysoft.spacejunk.objects
 */
class JunkStack extends Group {

  private final Platform platform;
  private final int x;
  private int deltaX;

  private Action repositionRocksAction;

  public JunkStack(Platform platform, int x) {
    this.platform = platform;
    this.x = x;
    this.deltaX = x * BasicJunk.SIZE;
    this.setTransform(false);

    repositionRocksAction = new Action() {

      @Override
      public boolean act(float v) {
        repositionRocks();
        return true;
      }

    };

  }

  public void repositionRocks() {
    Iterator<Actor> iterator = getChildren().iterator();
    int y = 1;
    while (iterator.hasNext()) {
      Actor next = iterator.next();
      next.addAction(moveTo(deltaX, y * BasicJunk.SIZE, 0.25f));
      y++;
    }
  }

  public boolean addJunk(BasicJunk fallenJunk) {
    BasicJunk newJunk = new BasicJunk(fallenJunk.type, fallenJunk.texture);
    addActor(newJunk);
    newJunk.setPosition(fallenJunk.getX() - platform.getX(), fallenJunk.getY() - platform.getY());
    repositionRocks();

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
          Actions.removeActor(),
          repositionRocksAction
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
