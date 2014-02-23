package com.ollysoft.spacejunk.objects.fuel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.Assets;

public class FuelTankView extends Actor {

  private final BitmapFont font;
  private final FuelTankModel model;
  private float displayLevel;

  public FuelTankView(Assets assets, FuelTankModel model) {
    this.model = model;
    this.font = assets.bigFont;
    this.displayLevel = model.getLevel();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    displayLevel = model.getLevel();
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
