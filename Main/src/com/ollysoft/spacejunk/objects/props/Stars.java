package com.ollysoft.spacejunk.objects.props;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

public class Stars extends Actor {

  private TextureRegion stars_background;

  public Stars(Assets assets) {
    this.stars_background = new TextureRegion(assets.starsBackground);
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    batch.setColor(new Color(1,1,1, parentAlpha));
    batch.draw(stars_background, 0, 0);
  }
}
