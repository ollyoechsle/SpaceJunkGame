package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.fuel.FuelTankModel;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.score.ScoreModel;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.RelativePosition;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Platform extends Group {

  protected final JunkPileModel junkPileModel;
  private final Assets assets;
  protected final GameScreen game;
  protected final FuelTankModel fuelTank;
  protected final JunkPileView junkPileView;
  private final Ship ship;

  protected RelativePosition relativePosition;

  public Platform(Assets assets, GameScreen game, ScoreModel scoreModel, FuelTankModel fuelTank) {
    super();
    this.assets = assets;
    this.game = game;
    this.fuelTank = fuelTank;

    this.setTransform(false);

    this.setWidth(FallingJunk.SIZE * 3);
    this.setHeight(FallingJunk.SIZE * 3);

    this.setX(0);

    ship = new Ship(assets);
    addActor(ship);

    junkPileView = new JunkPileView(this);
    addActor(junkPileView);

    this.junkPileModel = new JunkPileModel(5, junkPileView, scoreModel);
    this.junkPileModel.objectAt(0, 2).fix();
    this.junkPileModel.objectAt(1, 2).fix();
    this.junkPileModel.objectAt(2, 2).fix();

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
    fuelTank.onFuelSpent();
    if (delta < 0) {
      this.ship.prepareToTurnLeft();
    } else {
      this.ship.prepareToTurnRight();
    }
    assets.whoosh.play();
    this.addAction(Actions.moveTo(this.getX() + delta, 0.5f));
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

