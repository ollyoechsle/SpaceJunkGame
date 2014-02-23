package com.ollysoft.spacejunk.objects.fuel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.Assets;

public class FuelTankView extends Actor {

  private static final float POINTS_PER_SECOND = 20;
  private final BitmapFont font;
  private final FuelTankModel model;
  private float displayLevel;

  public FuelTankView(Assets assets, FuelTankModel model) {
    this.model = model;
    this.font = assets.bigFont;
    this.displayLevel = 0f;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    float actualScore = model.getLevel();
    if (actualScore <= displayLevel) {
      displayLevel -= POINTS_PER_SECOND * delta;
    } else {
      displayLevel = (float) Math.floor(actualScore);
    }
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = "" + (int) displayLevel;
    BitmapFont.TextBounds bounds = font.getBounds(fontText);
    float fontX = GameScreen.width - font.getBounds(fontText).width;
    float fontY = GameScreen.height - bounds.height - 100;
    int margin = 10;
    font.draw(batch, fontText, fontX - margin, fontY - margin);
  }

}
