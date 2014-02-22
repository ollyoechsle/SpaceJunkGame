package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public class JunkGroup extends Group implements MoundListener {

  public JunkGroup() {
    this.setTransform(false);
  }

  @Override
  public void onObjectAdded(BasicJunk newJunk, int dx, int dy) {
    addActor(newJunk);
    newJunk.setPosition(newJunk.getX() * BasicJunk.SIZE, newJunk.getY() * BasicJunk.SIZE);
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
