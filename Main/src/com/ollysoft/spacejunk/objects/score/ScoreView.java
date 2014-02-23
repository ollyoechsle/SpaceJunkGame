package com.ollysoft.spacejunk.objects.score;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

public class ScoreView extends Actor {

  private static final float POINTS_PER_SECOND = 100;
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
    if (Math.abs(actualScore - displayScore) > 0.1) {
      int sign = actualScore > displayScore ? 1 : -1;
      displayScore += sign * POINTS_PER_SECOND * delta;
    } else {
      displayScore = actualScore;
    }
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = "" + (int) displayScore;
    BitmapFont.TextBounds bounds = font.getBounds(fontText);
    float fontX = getParent().getWidth() - bounds.width;
    float fontY = getY();//getParent().getHeight() - bounds.height;
    font.draw(batch, fontText, fontX, fontY);
  }

}
