package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.objects.score.ScoreModel;
import com.ollysoft.spacejunk.util.Movement;
import com.ollysoft.spacejunk.util.RelativePosition;

public class JunkPileModel {

  private final int VOID = -1000;

  private final JunkPileListener listener;
  private final MoundObject[][] grid;
  private final int halfWidth;
  private final ScoreModel scoreModel;
  private final int arrayLength;

  public JunkPileModel(int halfWidth, JunkPileListener listener, ScoreModel scoreModel) {
    this.listener = listener;
    this.halfWidth = halfWidth;
    this.scoreModel = scoreModel;
    this.arrayLength = halfWidth + halfWidth + 1;
    this.grid = new MoundObject[arrayLength][arrayLength];
    initialiseGrid();
  }

  /**
   * Return whether the block could land at the given position.
   */
  public boolean isAttractedTo(RelativePosition relativePosition, Movement movement) {
    return isAttractedTo(relativePosition.dx, relativePosition.dy, movement);
  }

  protected boolean isAttractedTo(int dx, int dy, Movement movement) {

    if (isOutOfBounds(dx) || isOutOfBounds(dy)) return false;
    if (!objectAt(dx, dy).empty) return false;

    //int highestPoint = getHighestPointBelow(dx, dy);
    //return highestPoint != VOID && dy <= highestPoint + 1;

    for (Movement m : Movement.values()) {
      if (!objectAt(dx + m.dx, dy + m.dy).empty) {
        return true;
      }
    }

    return false;
  }

  private boolean isOutOfBounds(int dx) {
    if ((dx + halfWidth) > arrayLength - 2) {
      return true;
    }

    return (dx - halfWidth) < -(arrayLength - 2);
  }

  private int getHighestPointBelow(int dx, int currentDy) {
    int ay = currentDy + halfWidth;
    for (int y = Math.min(ay, arrayLength - 1); y >= 0; y--) {
      if (!grid[dx + halfWidth][y].empty) {
        return y - halfWidth;
      }
    }
    return VOID;
  }

  private void initialiseGrid() {
    for (int x = 0; x < arrayLength; x++) {
      grid[x] = new MoundObject[arrayLength];
      for (int y = 0; y < arrayLength; y++) {
        grid[x][y] = new MoundObject(x - halfWidth, y - halfWidth);
      }
    }
  }

  public MoundObject objectAt(RelativePosition position) {
    return objectAt(position.dx, position.dy);
  }

  protected MoundObject objectAt(int dx, int dy) {
    return grid[dx + halfWidth][dy + halfWidth];
  }

  private Array<BasicJunk> junks = new Array<BasicJunk>(3);

  /**
   * Removes a group of objects
   *
   * @param group
   */
  public void remove(ObjectGroup group) {
    junks.clear();
    for (MoundObject object : group.objects) {
      listener.onObjectRemoved(object.junk, object.dx, object.dy);
      junks.add(object.junk);
      object.clear();
    }
    scoreModel.onCollectedScore(junks, countJunk());
  }

  public int countJunk() {
    int count = 0;
    for (int y = 0; y < arrayLength; y++) {
      for (int x = 0; x < arrayLength; x++) {
        if (grid[x][y].junk != null) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Reassigns the set of objects to new positions
   */
  public void applyGravity() {
    for (int y = 0; y < arrayLength; y++) {
      for (int x = 0; x < arrayLength; x++) {
        MoundObject object = grid[x][y];
        if (!object.fixed && !object.empty) {
          // look below the object
          if (isNothingHoldingUp(x, y)) {

            // if there is no surface to fall to
            object.fallOff();

          }
        }
      }
    }
  }

  private boolean isNothingHoldingUp(int x, int y) {
    return false;//return y == 0 || grid[x][y - 1].empty;
  }

  /**
   * Gets all the groups of the same type
   *
   * @return
   */
  public Array<ObjectGroup> getGroups() {

    clearGroups();
    Array<ObjectGroup> groups = new Array<ObjectGroup>();

    for (int x = 0; x < arrayLength; x++) {
      for (int y = 0; y < arrayLength; y++) {
        MoundObject object = grid[x][y];

        if (object.group == null && object.junk != null) {
          ObjectGroup group = new ObjectGroup();
          findBlocksOfSameType(x, y, object.junk.type, group);
          if (group.isBigEnough()) {
            groups.add(group);
          }
        }

      }
    }

    return groups;

  }

  private void clearGroups() {
    for (int x = 0; x < arrayLength; x++) {
      for (int y = 0; y < arrayLength; y++) {
        grid[x][y].group = null;
      }
    }
  }

  private void findBlocksOfSameType(int x, int y, JunkType expectedType, ObjectGroup group) {

    MoundObject object = grid[x][y];

    if (object.empty || object.fixed || object.junk.type != expectedType || object.group != null) {
      return;
    }

    group.add(object);

    if (x > 0) {
      findBlocksOfSameType(x - 1, y, expectedType, group); // left
    }
    if (x < arrayLength - 1) {
      findBlocksOfSameType(x + 1, y, expectedType, group); // right
    }

    if (y > 0) {
      findBlocksOfSameType(x, y - 1, expectedType, group); // down
    }
    if (y < arrayLength - 1) {
      findBlocksOfSameType(x, y + 1, expectedType, group); // up
    }

  }

  public class ObjectGroup {
    public Array<MoundObject> objects;

    public ObjectGroup() {
      this.objects = new Array<MoundObject>();
    }

    public void add(MoundObject object) {
      this.objects.add(object);
      object.group = this;
    }

    public boolean isBigEnough() {
      return this.objects.size >= 3;
    }

  }

  public class MoundObject {

    /**
     * Distance from the center of the mound
     */
    private final int dx;

    /**
     * Distance from the center of the mound
     */
    private final int dy;

    public boolean fixed = false;
    public boolean empty = true;
    public BasicJunk junk;
    public ObjectGroup group = null;

    public MoundObject(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }

    public MoundObject place(BasicJunk fallenJunk) {

      if (!this.empty) {
        throw new RuntimeException("Cannot place object - it is already filled");
      }

      BasicJunk newJunk = new BasicJunk(fallenJunk.type, fallenJunk.texture);
      this.junk = newJunk;
      this.empty = false;

      listener.onObjectAdded(newJunk, fallenJunk, this.dx, this.dy);
      return this;
    }

    public MoundObject fix() {
      this.fixed = true;
      this.empty = false;
      return this;
    }

    public boolean isSame(MoundObject other) {

      if (this.empty || other.empty) {
        return false;
      }

      return this.junk.type == other.junk.type;

    }

    public void fallOff() {
      if (empty) {
        throw new RuntimeException("Empty object cannot fall off");
      }
      listener.onObjectFallenFromMound(junk, dx, dy);
      clear();
    }

    private void clear() {
      if (fixed) {
        throw new RuntimeException("Cannot clear fixed object");
      }
      empty = true;
      junk = null;
    }

  }

}
