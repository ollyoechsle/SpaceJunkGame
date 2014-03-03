package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.objects.score.ScoreModel;
import com.ollysoft.spacejunk.util.Movement;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JunkPileModelTest {

  private static final BasicJunk PLAIN_ROCK = new BasicJunk(JunkType.PLAIN_ROCK, null);
  private static final BasicJunk GOLD_ROCK = new BasicJunk(JunkType.GOLD_ROCK, null);

  private JunkPileModel m;
  private TestJunkPileListener moundListener;
  private TestScoreModel score;

  @Test
  public void isAttractedToObjectDirectlyBeneath() {

    givenMound(2);

    assertFalse(m.isAttractedTo(0, 1, Movement.DOWN));

    m.objectAt(0, 0).place(PLAIN_ROCK);

    assertTrue(m.isAttractedTo(0, 1, Movement.DOWN));

  }

  @Test
  public void cannotLandIfNothingBelow() {

    givenMound(2);

    m.objectAt(-1, 0).place(PLAIN_ROCK);
    m.objectAt(+1, 0).place(PLAIN_ROCK);
    assertFalse(m.isAttractedTo(0, 2, Movement.DOWN));

  }

  @Test
  public void cannotLandIfAlreadyUnderneath() {
    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    assertFalse(m.isAttractedTo(0, 1, Movement.DOWN));

  }

  @Test
  public void cannotLandIfSomethingAlreadyThere() {
    givenMound(2);

    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertFalse(m.isAttractedTo(0, 0, Movement.DOWN));

  }

  @Test
  public void cannotLandIfTooFarAbove() {
    givenMound(2);
    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertFalse(m.isAttractedTo(0, 2, Movement.DOWN));
    assertTrue(m.isAttractedTo(0, 1, Movement.DOWN));
  }

  @Test
  public void cannotLandIfXOutOfBounds() {

    givenMound(2);
    assertFalse(m.isAttractedTo(5, 2, Movement.DOWN));
    assertFalse(m.isAttractedTo(-5, 2, Movement.DOWN));

    m.objectAt(-2, 0).place(PLAIN_ROCK);
    m.objectAt(+2, 0).place(PLAIN_ROCK);
    assertTrue(m.isAttractedTo(-2, 1, Movement.DOWN));
    assertTrue(m.isAttractedTo(+2, 1, Movement.DOWN));

  }

  @Test
  public void cannotLandIfYOutOfBounds() {

    givenMound(2);
    assertFalse(m.isAttractedTo(0, -5, Movement.DOWN));
    assertFalse(m.isAttractedTo(0, +5, Movement.DOWN));

  }

  @Test
  public void doesNotLandOnAdjacent() {

    givenMound(2);
    m.objectAt(0, 0);
    assertFalse(m.isAttractedTo(-1, 1, Movement.DOWN));

  }

  @Test
  public void cannotLandIfYOutOfBoundsInBothDirections() {

    givenMound(2);
    assertFalse(m.isAttractedTo(12, 12, Movement.DOWN));

  }

  @Test
  public void getGroups() {
    givenMound(2);
    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    assertEquals(0, m.getGroups().size);

    m.objectAt(2, 0).place(PLAIN_ROCK);
    assertEquals(1, m.getGroups().size);

    m.objectAt(0, 1).place(GOLD_ROCK);
    m.objectAt(1, 1).place(GOLD_ROCK);
    assertEquals(1, m.getGroups().size);

    m.objectAt(2, 1).place(GOLD_ROCK);
    assertEquals(2, m.getGroups().size);

  }

  @Test
  public void removeGroups() {

    givenMound(2);

    // one 1x3 row of plain rocks
    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    m.objectAt(2, 0).place(PLAIN_ROCK);

    // above, 1x2 row of gold rocks
    m.objectAt(0, 1).place(GOLD_ROCK);
    m.objectAt(1, 1).place(GOLD_ROCK);

    whenWeRemoveGroup(0);

    assertEquals(3, moundListener.removedCount);
    assertEquals(3, score.score);

    m.applyGravity();

    assertEquals(2, moundListener.fallenCount);

  }

  @Test
  public void countBlocks() {

    givenMound(2);

    // one 1x3 row of plain rocks
    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    m.objectAt(2, 0).place(PLAIN_ROCK);

    assertEquals(3, m.countJunk());

  }

  @Test
  public void singleObjectFallsUnderGravity() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);

    m.applyGravity();

    assertEquals(1, moundListener.fallenCount);

    assertTrue(m.objectAt(0, 2).empty);

  }

  @Test
  public void fixedObjectPreventsFallOfAnotherObject() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    m.objectAt(0, 1).place(PLAIN_ROCK).fix();

    m.applyGravity();

    assertEquals(0, moundListener.fallenCount);

    assertFalse(m.objectAt(0, 2).empty);

  }

  @Test
  public void fixedObjectDoesntFallButUnfixedOneDoes() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    m.objectAt(1, 2).place(PLAIN_ROCK).fix();

    m.applyGravity();

    assertEquals(1, moundListener.fallenCount);

    assertTrue(m.objectAt(0, 2).empty);
    assertFalse(m.objectAt(1, 2).empty);

  }

  private void whenWeRemoveGroup(int index) {
    Array<JunkPileModel.ObjectGroup> groups = m.getGroups();
    assertEquals(1, groups.size);
    JunkPileModel.ObjectGroup group = groups.get(index);
    m.remove(group);
  }

  @Test
  public void gridIsInitialised() throws Exception {
    givenMound(1);
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        JunkPileModel.MoundObject moundObject = m.objectAt(x, y);
        assertNotNull(moundObject);
        assertTrue(moundObject.empty);
      }
    }
  }

  @Test
  public void placeJunkOnMound() throws Exception {
    givenMound(1);
    JunkPileModel.MoundObject moundObject = m.objectAt(0, 0);
    moundObject.place(PLAIN_ROCK);
    assertFalse(moundObject.empty);
  }

  @Test
  public void testMoundsTheSame() {
    givenMound(1);
    assertFalse(m.objectAt(0, 0).isSame(m.objectAt(1, 0)));

    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    assertTrue(m.objectAt(0, 0).isSame(m.objectAt(1, 0)));

    m.objectAt(0, 1).place(GOLD_ROCK);
    assertFalse(m.objectAt(0, 0).isSame(m.objectAt(0, 1)));

  }

  @Test
  public void testCallsAddCallback() {
    givenMound(1);
    assertEquals(0, moundListener.addedCount);

    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertEquals(1, moundListener.addedCount);
  }

  @Test
  public void fixedBlocksDoNotGroup() {
    givenMound(2);

    m.objectAt(0, 0).place(PLAIN_ROCK).fix();
    m.objectAt(1, 0).place(PLAIN_ROCK).fix();
    m.objectAt(2, 0).place(PLAIN_ROCK).fix();
    assertEquals(0, m.getGroups().size);
  }

  private void givenMound(int size) {
    moundListener = new TestJunkPileListener();
    score = new TestScoreModel();
    m = new JunkPileModel(size, moundListener, score);
  }

  class TestScoreModel implements ScoreModel {

    int score = 0;

    @Override
    public void onCollectedScore(Array<BasicJunk> items, int junkRemaining) {
      score += items.size;
    }

    @Override
    public int getScore() {
      return score;
    }
  }

  class TestJunkPileListener implements JunkPileListener {

    private int fallenCount = 0;
    private int removedCount = 0;
    private int addedCount = 0;

    @Override
    public void onObjectAdded(BasicJunk junk, BasicJunk fallenJunk, int dx, int dy) {
      addedCount++;
    }

    @Override
    public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy) {
      fallenCount++;
    }

    @Override
    public void onObjectRemoved(BasicJunk junk, int dx, int dy) {
      removedCount++;
    }

  }

}
