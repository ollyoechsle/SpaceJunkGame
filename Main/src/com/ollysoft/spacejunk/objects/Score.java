package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.util.Assets;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Score extends Actor {

  private final BitmapFont font;
  public int score = 0;

  public Score(int initialScore, Assets assets) {
    this.font = assets.bigFont;
    this.score = initialScore;
  }

  public void changeScore(int delta) {
    score += delta;
  }

  public void onCollectedBlock(FallingJunk block) {
    this.score += block.type.getCollectionScore();
  }

  public void onMissedBlock(FallingJunk block) {
    this.score -= block.type.getMissCost();
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    String fontText = "" + score;
    BitmapFont.TextBounds bounds = font.getBounds(fontText);
    float fontX = GameScreen.width - font.getBounds(fontText).width;
    float fontY = GameScreen.height - bounds.height;
    int margin = 10;
    font.draw(batch, fontText, fontX - margin, fontY - margin);
  }

}
