package com.ollysoft.spacejunk.objects.score;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

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

  @Override
  public void onCollectedScore(Array<BasicJunk> items, int remainingItemCount) {
    float multiplier = 1.0f;
    if (items.size > 3) {
      multiplier += (items.size - 3) * 0.5f;
    }
    int pointsScored = 0;
    for (BasicJunk item : items) {
      pointsScored += (int) (multiplier * item.type.getCollectionScore());
    }
    if (remainingItemCount == 0) {
      pointsScored *= 2;
    }
    pointsScoredListener.onPointsScored(pointsScored, items);
    score += pointsScored;
  }


}
