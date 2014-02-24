package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.util.Assets;

public class Ship extends Group {

  private final TextureRegion texture;
  private final Thruster thruster1, thruster2;
  private final Assets assets;

  public Ship(Assets assets) {
    super();
    this.assets = assets;
    this.texture = new TextureRegion(assets.ship);
    this.setX(0);
    this.setY(0);
    this.setWidth(FallingJunk.SIZE * 3);
    this.setHeight(FallingJunk.SIZE * 3);

    thruster1 = new Thruster(assets, 64f, getHeight() / 2);
    this.addActor(thruster1);
    thruster2 = new Thruster(assets, 128f, getHeight() / 2);
    this.addActor(thruster2);

  }

  public void moveThrusters(float deltaX) {
    if (deltaX < 0) {
      this.prepareToTurnLeft();
    } else {
      this.prepareToTurnRight();
    }
  }

  private void prepareToTurnRight() {
    thruster1.pointLeft();
    thruster2.pointDown();
  }

  private void prepareToTurnLeft() {
    thruster1.pointDown();
    thruster2.pointRight();
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    super.draw(batch, parentAlpha);
  }

}
