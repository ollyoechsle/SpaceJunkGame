package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.scoring.ScoreModel;
import com.ollysoft.spacejunk.util.RelativePosition;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends Group {

  protected final JunkPileModel junkPileModel;
  protected final GameScreen game;
  protected final JunkPileView junkPileView;

  protected RelativePosition relativePosition;

  public Platform(TextureRegion texture, int width, GameScreen game, ScoreModel scoreModel) {
    super();
    this.game = game;

    this.setTransform(false);

    this.setWidth(FallingJunk.SIZE * width);
    this.setHeight(FallingJunk.SIZE);

    this.setX(GameScreen.width - (this.getWidth() / 2));
    this.setY(FallingJunk.SIZE * 2);

    addActor(new Paddle(texture, width));

    junkPileView = new JunkPileView(this);
    addActor(junkPileView);

    this.junkPileModel = new JunkPileModel(width, junkPileView, scoreModel);

    for (int x = 0; x < width; x++) {
      this.junkPileModel.objectAt(x, 0).fix();
    }

    relativePosition = new RelativePosition();

  }

  public RelativePosition getRelativePosition(Rectangle objectBoundingBox) {
    float x = (objectBoundingBox.getX() - this.getX());
    float y = (objectBoundingBox.getY() - this.getY());

    relativePosition.dx = (int) x / BasicJunk.SIZE;
    relativePosition.dy = (int) y / BasicJunk.SIZE;
    return relativePosition;
  }

  public boolean canLandOn(RelativePosition relativePosition) {
    return this.junkPileModel.canLandOn(relativePosition);
  }

  public void moveX(float delta) {
    this.addAction(Actions.moveTo(this.getX() + delta, 0.2f));
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
    this.junkPileModel.objectAt(position).place(junk);
    Array<JunkPileModel.ObjectGroup> groups = this.junkPileModel.getGroups();
    for (JunkPileModel.ObjectGroup group : groups) {
      this.junkPileModel.remove(group);
    }
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    this.junkPileModel.applyGravity();
  }
}

