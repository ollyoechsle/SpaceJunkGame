package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MoundTest {

  private static final BasicJunk PLAIN_ROCK = new BasicJunk(JunkType.PLAIN_ROCK, null);
  private static final BasicJunk GOLD_ROCK = new BasicJunk(JunkType.GOLD_ROCK, null);

  private Mound m;
  private MoundTest.TestMoundListener listener;

  @Test
  public void canLandOnObjectDirectlyBeneath() {

    givenMound(2);

    assertFalse(m.canLandOn(0, 1));

    m.objectAt(0, 0).place(PLAIN_ROCK);

    assertTrue(m.canLandOn(0, 1));

  }

  @Test
  public void cannotLandIfNothingBelow() {

    givenMound(2);

    m.objectAt(-1, 0).place(PLAIN_ROCK);
    m.objectAt(+1, 0).place(PLAIN_ROCK);
    assertFalse(m.canLandOn(0, 2));

  }

  @Test
  public void cannotLandIfAlreadyUnderneath() {
    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    assertFalse(m.canLandOn(0, 1));

  }

  @Test
  public void cannotLandIfSomethingAlreadyThere() {
    givenMound(2);

    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertFalse(m.canLandOn(0, 0));

  }

  @Test
  public void cannotLandIfTooFarAbove() {
    givenMound(2);
    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertFalse(m.canLandOn(0, 2));
    assertTrue(m.canLandOn(0, 1));
  }

  @Test
  public void cannotLandIfXOutOfBounds() {

    givenMound(2);
    assertFalse(m.canLandOn(5, 2));
    assertFalse(m.canLandOn(-5, 2));

    m.objectAt(-2, 0).place(PLAIN_ROCK);
    m.objectAt(+2, 0).place(PLAIN_ROCK);
    assertTrue(m.canLandOn(-2, 1));
    assertTrue(m.canLandOn(+2, 1));

  }

  @Test
  public void cannotLandIfYOutOfBounds() {

    givenMound(2);
    assertFalse(m.canLandOn(0, -5));
    assertFalse(m.canLandOn(0, +5));

  }

  @Test
  public void cannotLandIfYOutOfBoundsInBothDirections() {

    givenMound(2);
    assertFalse(m.canLandOn(12, 12));

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

    assertEquals(3, listener.removedCount);

    m.applyGravity();

    assertEquals(2, listener.fallenCount);

  }

  @Test
  public void singleObjectFallsUnderGravity() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);

    m.applyGravity();

    assertEquals(1, listener.fallenCount);

    assertTrue(m.objectAt(0, 2).empty);

  }

  @Test
  public void fixedObjectPreventsFallOfAnotherObject() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    m.objectAt(0, 1).place(PLAIN_ROCK).fix();

    m.applyGravity();

    assertEquals(0, listener.fallenCount);

    assertFalse(m.objectAt(0, 2).empty);

  }

  @Test
  public void fixedObjectDoesntFallButUnfixedOneDoes() {

    givenMound(2);

    m.objectAt(0, 2).place(PLAIN_ROCK);
    m.objectAt(1, 2).place(PLAIN_ROCK).fix();

    m.applyGravity();

    assertEquals(1, listener.fallenCount);

    assertTrue(m.objectAt(0, 2).empty);
    assertFalse(m.objectAt(1, 2).empty);

  }

  private void whenWeRemoveGroup(int index) {
    Array<Mound.ObjectGroup> groups = m.getGroups();
    assertEquals(1, groups.size);
    Mound.ObjectGroup group = groups.get(index);
    m.remove(group);
  }

  @Test
  public void gridIsInitialised() throws Exception {
    givenMound(1);
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        Mound.MoundObject moundObject = m.objectAt(x, y);
        assertNotNull(moundObject);
        assertTrue(moundObject.empty);
      }
    }
  }

  @Test
  public void placeJunkOnMound() throws Exception {
    givenMound(1);
    Mound.MoundObject moundObject = m.objectAt(0, 0);
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
    assertEquals(0, listener.addedCount);

    m.objectAt(0, 0).place(PLAIN_ROCK);
    assertEquals(1, listener.addedCount);
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
    listener = new TestMoundListener();
    m = new Mound(size, listener);
  }

  class TestMoundListener implements MoundListener {

    private int fallenCount = 0;
    private int removedCount = 0;
    private int addedCount = 0;

    @Override
    public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy) {
      fallenCount++;
    }

    @Override
    public void onObjectRemoved(BasicJunk junk, int dx, int dy) {
      removedCount++;
    }

    @Override
    public void onObjectAdded(BasicJunk junk, int dx, int dy) {
      addedCount++;
    }
  }

}
