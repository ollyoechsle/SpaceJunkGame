package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends Group {

  protected final Mound mound;
  protected final GameScreen game;
  protected final JunkGroup junkGroup;

  protected RelativePosition relativePosition;

  public Platform(TextureRegion texture, int width, GameScreen game) {
    super();
    this.game = game;

    this.setTransform(false);

    this.setWidth(FallingJunk.SIZE * width);
    this.setHeight(FallingJunk.SIZE);

    this.setX(GameScreen.width - (this.getWidth() / 2));
    this.setY(FallingJunk.SIZE * 5);

    addActor(new Paddle(texture, width));

    junkGroup = new JunkGroup();
    addActor(junkGroup);

    this.mound = new Mound(width, junkGroup);

    for (int x = 0; x < width; x++) {
      this.mound.objectAt(x, 0).place(new BasicJunk(JunkType.GOLD_ROCK, texture)).fix();
    }

    relativePosition = new RelativePosition();

  }

  public RelativePosition getRelativePosition(Rectangle objectBoundingBox) {
    float halfWidth = (objectBoundingBox.getWidth() - 1) / 2;
    float x = (objectBoundingBox.getX() - this.getX() + halfWidth);
    float y = (objectBoundingBox.getY() - this.getY());

    relativePosition.dx = (int) x / BasicJunk.SIZE;
    relativePosition.dy = (int) y / BasicJunk.SIZE;
    return relativePosition;
  }

  public boolean canLandOn(RelativePosition relativePosition) {
    return this.mound.canLandOn(relativePosition);
  }

  public void applyGravity() {
    mound.applyGravity();
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

  public void addJunk(BasicJunk junk, RelativePosition position) {
    this.mound.objectAt(position).place(junk);
  }


}

