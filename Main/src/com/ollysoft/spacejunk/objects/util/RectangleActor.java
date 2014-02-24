package com.ollysoft.spacejunk.objects.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class RectangleActor extends Actor {

  private Rectangle rectangle;

  public RectangleActor() {
    this.rectangle = new Rectangle(0, 0, 0, 0);
  }

  public Rectangle getBoundingBox() {
    rectangle.setX(getX());
    rectangle.setY(getY());
    rectangle.setWidth(getWidth());
    rectangle.setHeight(getHeight());
    return rectangle;
  }

}
