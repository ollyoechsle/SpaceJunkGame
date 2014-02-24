package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.util.Assets;

public class Thruster extends Actor {

  protected TextureRegion texture;

  public Thruster(Assets assets, float centerX, float centerY) {
    super();
    this.texture = new TextureRegion(assets.thruster);
    this.setWidth(FallingJunk.SIZE * 3);
    this.setHeight(FallingJunk.SIZE * 3);
    this.setX(centerX - this.getWidth() / 2);
    this.setY(centerY - this.getHeight() / 2);
  }

  public void pointRight() {
    this.addAction(Actions.rotateTo(90, 0.2f));
  }

  public void pointDown() {
    this.addAction(Actions.rotateTo(0, 0.2f));
  }

  public void pointLeft() {
    this.addAction(Actions.rotateTo(-90, 0.2f));
  }

  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, getRotation());
  }

}
