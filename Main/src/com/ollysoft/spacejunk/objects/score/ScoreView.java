package com.ollysoft.spacejunk.objects.score;

import com.ollysoft.spacejunk.objects.util.LabelView;
import com.ollysoft.spacejunk.util.Assets;

public class ScoreView extends LabelView {

  private static final float POINTS_PER_SECOND = 100;
  private final ScoreModel model;
  private float displayScore;

  public ScoreView(Assets assets, ScoreModel model) {
    super(assets);
    this.model = model;
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
  public String getText() {
    return "" + (int) displayScore;
  }

}
