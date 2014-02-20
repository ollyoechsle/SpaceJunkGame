package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;

public class Mound {


  private final MoundObject[][] grid;
  private final int halfWidth;
  private final int arrayLength;

  public Mound(int halfWidth) {
    this.halfWidth = halfWidth;
    this.arrayLength = halfWidth + halfWidth + 1;
    this.grid = new MoundObject[arrayLength][arrayLength];
    initialiseGrid();
  }

  private void initialiseGrid() {
    for (int x = 0; x < arrayLength; x++) {
      grid[x] = new MoundObject[arrayLength];
      for (int y = 0; y < arrayLength; y++) {
        grid[x][y] = new MoundObject();
      }
    }
  }

  public MoundObject objectAt(int x, int y) {
    return grid[x + halfWidth][y + halfWidth];
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

        if (object.group == null && !object.empty) {
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

    if (object.empty || object.junk.type != expectedType || object.group != null) {
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

    public boolean empty = true;
    public BasicJunk junk;
    public ObjectGroup group = null;

    public void place(BasicJunk junk) {
      this.junk = junk;
      this.empty = false;
    }

    public boolean isSame(MoundObject other) {

      if (this.empty || other.empty) {
        return false;
      }

      return this.junk.type == other.junk.type;

    }

  }

}
