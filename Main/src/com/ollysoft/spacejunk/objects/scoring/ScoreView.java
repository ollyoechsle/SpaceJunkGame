package com.ollysoft.spacejunk.objects.scoring;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.Assets;

public class ScoreView extends Actor {

  private final BitmapFont font;
  private final ScoreModel model;

  public ScoreView(Assets assets, ScoreModel model) {
    this.model = model;
    this.font = assets.bigFont;
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = "" + model.getScore();
    BitmapFont.TextBounds bounds = font.getBounds(fontText);
    float fontX = GameScreen.width - font.getBounds(fontText).width;
    float fontY = GameScreen.height - bounds.height;
    int margin = 10;
    font.draw(batch, fontText, fontX - margin, fontY - margin);
  }

}
