package com.ollysoft.spacejunk.objects.platform;

/**
 * com.ollysoft.spacejunk.objects.platform
 */
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

  private class MoundObject {

    public boolean empty = true;

  }

}
