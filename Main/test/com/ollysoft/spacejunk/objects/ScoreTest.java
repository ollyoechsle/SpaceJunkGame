package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.objects.score.BasicScoreModel;
import com.ollysoft.spacejunk.objects.score.PointsScoredListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScoreTest {

  private BasicScoreModel score;

  @Before
  public void setUp() {
    score = new BasicScoreModel(0, new PointsScoredListener() {
      @Override
      public void onPointsScored(int points, Array<BasicJunk> items) {

      }
    });
  }

  @Test
  public void testOnCollectedBlock() throws Exception {
    score.onCollectedScore(blocks(JunkType.GOLD_ROCK, 3), 1);
    int expected = JunkType.GOLD_ROCK.getCollectionScore() * 3;
    Assert.assertEquals(expected, score.getScore());
  }

  @Test
  public void extraCreditForMoreThanThreeBlocks() throws Exception {
    score.onCollectedScore(blocks(JunkType.GOLD_ROCK, 4), 1);
    int expected = (int) (JunkType.GOLD_ROCK.getCollectionScore() * 4 * 1.5);
    Assert.assertEquals(expected, score.getScore());
  }

  @Test
  public void scoreDoubledIfNoJunkRemaining() throws Exception {
    score.onCollectedScore(blocks(JunkType.GOLD_ROCK, 3), 0);
    int expected = JunkType.GOLD_ROCK.getCollectionScore() * 3;
    Assert.assertEquals(expected * 2, score.getScore());
  }

  private Array<BasicJunk> blocks(JunkType goldRock, int i) {
    Array<BasicJunk> collectedBlocks = new Array<BasicJunk>();
    for (int x = 0; x < i; x++) {
      collectedBlocks.add(new BasicJunk(goldRock, null));
    }
    return collectedBlocks;
  }


}
