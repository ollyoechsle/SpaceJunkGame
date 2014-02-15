package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.ollysoft.spacejunk.objects
 */
public class Score extends Actor {

  private final BitmapFont font;
  public int score = 0;

  public Score(int initialScore) {
    font = new BitmapFont(Gdx.files.internal("acknowledge.fnt"));
    this.score = initialScore;
  }

  public void onCollectedBlock(Block block) {
    this.score += 10;
  }

  public void onMissedBlock(Block block) {
    this.score -= 10;
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    font.draw(batch, "" + score, 600, 600);
  }

}
