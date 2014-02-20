package com.ollysoft.spacejunk.objects.platform;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public class Mound {


  private final MoundObject[][] grid;
  private final int halfWidth;

  public Mound(int halfWidth) {
    this.halfWidth = halfWidth;
    int maxSize = halfWidth + halfWidth + 1;
    grid = new MoundObject[maxSize][maxSize];
    initialiseGrid(maxSize);
  }

  private void initialiseGrid(int maxSize) {
    for (int x = 0; x < maxSize; x++) {
      grid[x] = new MoundObject[maxSize];
      for (int y = 0; y < maxSize; y++) {
        grid[x][y] = new MoundObject();
      }
    }
  }

  public MoundObject objectAt(int x, int y) {
    return grid[x + halfWidth][y + halfWidth];
  }

  public class MoundObject {

    public boolean empty = true;
    public BasicJunk junk;

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
