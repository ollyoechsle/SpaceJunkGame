package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class JunkGroup extends Group implements MoundListener {

  private Platform platform;

  public JunkGroup(Platform platform) {
    this.platform = platform;
    this.setTransform(false);
  }

  @Override
  public void onObjectAdded(BasicJunk newJunk, BasicJunk fallenJunk, int dx, int dy) {
    addActor(newJunk);
    // first position relative to the platform - exactly where it was at the point when it touched
    newJunk.setPosition(fallenJunk.getX() - platform.getX(), fallenJunk.getY() - platform.getY());
    // later, move to the correct position if not exactly in the right place
    newJunk.addAction(moveTo(dx * BasicJunk.SIZE, dy * BasicJunk.SIZE, 0.25f));
  }

  @Override
  public void onObjectRemoved(BasicJunk junk, int dx, int dy) {
    removeActor(junk);
  }

  @Override
  public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy) {
    removeActor(junk);
  }

}
