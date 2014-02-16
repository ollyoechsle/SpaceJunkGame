package com.ollysoft.spacejunk.objects.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * com.ollysoft.spacejunk.objects
 */
public class RectangleGroup extends Group {

  private Rectangle rectangle;

  public RectangleGroup() {
    this.rectangle = new Rectangle(0, 0, 0, 0);
  }

  public Rectangle getRectangle() {
    rectangle.setX(getX());
    rectangle.setY(getY());
    rectangle.setWidth(getWidth());
    rectangle.setHeight(getHeight());
    return rectangle;
  }

}
