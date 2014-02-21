package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public class JunkStack2 extends Group implements MoundListener {

  private final Platform platform;
  private final int x;
  private final int deltaX;

  public JunkStack2(final Platform platform, int x) {
    this.platform = platform;
    this.x = x;
    this.deltaX = x * BasicJunk.SIZE;
    this.setTransform(false);
  }

  @Override
  public void onObjectAdded(BasicJunk junk, int dx, int dy) {
    addActor(junk);
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
