package com.ollysoft.spacejunk.util;

public class Movement {

  private final Direction direction;

  public enum Direction {
    UP(0, +1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(+1, 0);

    protected final int dx;
    protected final int dy;

    Direction(int dx, int dy) {

      this.dx = dx;
      this.dy = dy;
    }

  }

  public Movement(Direction direction) {
    this.direction = direction;
  }

  public float getDeltaX(float velocity) {
    return velocity * direction.dx;
  }

  public float getDeltaY(float velocity) {
    return velocity * direction.dy;
  }

}
