package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.fuel.FuelTankModel;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.score.ScoreModel;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.Movement;
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

    this.junkPileModel = new JunkPileModel(8, junkPileView, scoreModel);

    for (int y = 0; y <= 2; y++) {
      this.junkPileModel.objectAt(0, y).fix();
      this.junkPileModel.objectAt(1, y).fix();
      this.junkPileModel.objectAt(2, y).fix();
    }

    relativePosition = new RelativePosition();

  }

  public RelativePosition getRelativePosition(Rectangle objectBoundingBox) {

    float x = (objectBoundingBox.getX() + (objectBoundingBox.getWidth() / 2) - this.getX());
    float y = (objectBoundingBox.getY() - this.getY());

    relativePosition.dx = (int) x / BasicJunk.SIZE;
    relativePosition.dy = (int) y / BasicJunk.SIZE;
    return relativePosition;
  }

  public boolean canLandOn(RelativePosition relativePosition, Movement movement) {
    return this.junkPileModel.isAttractedTo(relativePosition, movement);
  }

  private float forceX = 0;
  private float forceY = 0;

  public void moveX(Movement movement, float velocity) {
    fuelTank.onFuelSpent();
    float deltaX = movement.getDeltaX(velocity);
    float deltaY = movement.getDeltaY(velocity);
    ship.moveThrusters(movement);
    assets.whoosh.play();
    forceX += deltaX;
    forceY += deltaY;
    //checkBounds();
  }

  @Deprecated
  public void moveTo(float x) {
    this.setX(
        x - (this.getWidth() / 2)
    );
    checkBounds();
  }

  private boolean checkBounds() {
    if (this.getX() < minX()) {
      this.setX(minX());
      return false;
    }
    if (this.getX() > maxX()) {
      this.setX(maxX());
      return false;
    }
    return true;
  }

  private float minX() {
    return -1 * BasicJunk.SIZE;
  }

  private float maxX() {
    return GameScreen.width - getWidth() + BasicJunk.SIZE;
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
    this.setX(this.getX() + (this.forceX * delta));
    this.setY(this.getY() + (this.forceY * delta));
    this.junkPileModel.applyGravity();
  }
}

