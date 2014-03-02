package com.ollysoft.spacejunk.objects.props;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ollysoft.spacejunk.objects.platform.Platform;
import com.ollysoft.spacejunk.util.Assets;

public class ParallaxBackground extends Group {

  private final Platform platform;

  public ParallaxBackground(Assets assets, Platform platform, int boulders) {
    this.platform = platform;
    setWidth(Gdx.graphics.getWidth() * 2);
    setHeight(Gdx.graphics.getHeight() * 2);
    for (int i = 0; i < boulders; i++) {
      addBoulders(assets);
    }
  }

  @Override
  public void act(float delta) {
    this.setX((+platform.getX() * 0.8f) - (getWidth() / 2));
    this.setY((+platform.getY() * 0.8f) - (getHeight() / 2));
  }

  private void addBoulders(Assets assets) {
    float x = (MathUtils.random(0, getWidth()));
    float y = (MathUtils.random(0, getHeight()));
    addActor(new Boulder(assets, x, y, 128));
  }

}
