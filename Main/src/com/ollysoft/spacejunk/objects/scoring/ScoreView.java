package com.ollysoft.spacejunk.objects.scoring;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.Assets;

public class ScoreView extends Actor {

  private static final float POINTS_PER_SECOND = 60;
  private final BitmapFont font;
  private final ScoreModel model;
  private float displayScore;

  public ScoreView(Assets assets, ScoreModel model) {
    this.model = model;
    this.font = assets.bigFont;
    this.displayScore = 0f;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    int actualScore = model.getScore();
    if (actualScore >= displayScore) {
      displayScore += POINTS_PER_SECOND * delta;
    } else {
      displayScore = actualScore;
    }
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = "" + (int) displayScore;
    BitmapFont.TextBounds bounds = font.getBounds(fontText);
    float fontX = GameScreen.width - font.getBounds(fontText).width;
    float fontY = GameScreen.height - bounds.height;
    int margin = 10;
    font.draw(batch, fontText, fontX - margin, fontY - margin);
  }

}
