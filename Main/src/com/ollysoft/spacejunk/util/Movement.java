package com.ollysoft.spacejunk.util;

public enum Movement {

  UP(0, +1),
  DOWN(0, -1),
  LEFT(-1, 0),
  RIGHT(+1, 0);

  private final int dx;
  private final int dy;

  Movement(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public float getDeltaX(float velocity) {
    return velocity * dx;
  }

  public float getDeltaY(float velocity) {
    return velocity * dy;
  }

}
