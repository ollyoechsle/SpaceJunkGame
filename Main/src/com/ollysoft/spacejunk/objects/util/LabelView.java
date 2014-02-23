package com.ollysoft.spacejunk.objects.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

public abstract class LabelView extends Actor {

  private final BitmapFont font;

  public LabelView(Assets assets) {
    this.font = assets.bigFont;
  }

  public float getWidth() {
    return font.getBounds(getText()).width;
  }

  public float getHeight() {
    return font.getBounds(getText()).height;
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = getText();
    font.draw(batch, fontText, getX(), getY() + font.getBounds(getText()).height);
  }

  public abstract String getText();

}
