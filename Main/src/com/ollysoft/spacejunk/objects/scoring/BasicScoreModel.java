package com.ollysoft.spacejunk.objects.scoring;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;

/**
 * com.ollysoft.spacejunk.objects
 */
public class BasicScoreModel implements ScoreModel {

  public int score = 0;
  private final PointsScoredListener pointsScoredListener;

  public BasicScoreModel(int initialScore, PointsScoredListener pointsScoredListener) {

    this.score = initialScore;
    this.pointsScoredListener = pointsScoredListener;
  }

  @Override
  public int getScore() {
    return score;
  }

  public void changeScore(int delta) {
    score += delta;
  }

  @Override
  public void onCollectedScore(Array<BasicJunk> items) {
    float multiplier = 1.0f;
    if (items.size > 3) {
      multiplier += (items.size - 3) * 0.1f;
    }
    int pointsScored = 0;
    for (BasicJunk item : items) {
      pointsScored += (int) (multiplier * item.type.getCollectionScore());
    }
    pointsScoredListener.onPointsScored(pointsScored, items);
    score += pointsScored;
  }

  public void onMissedBlock(FallingJunk block) {
    this.score -= block.type.getMissCost();
  }

}
