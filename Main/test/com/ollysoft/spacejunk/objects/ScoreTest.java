package com.ollysoft.spacejunk.objects;

import com.ollysoft.spacejunk.objects.scoring.BasicScoreModel;
import junit.framework.Assert;
import org.junit.Ignore;

public class ScoreTest {

  @Ignore
  public void testOnCollectedBlock() throws Exception {
    BasicScoreModel score = new BasicScoreModel(0, null);
    score.changeScore(10);
    Assert.assertEquals(10, score.score);
  }
}
