package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.math.Rectangle;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlatformTest {

  private Platform p;
  private int x = 100;

  @Test
  public void testAddJunk() throws Exception {

    Platform p = new Platform(null, 4);
    assertEquals(5, p.getChildren().size);
    p.setX(100);
    assertEquals(0, p.stacks[0].size());

    p.addJunk(someJunkAt(105));
    assertEquals(1, p.stacks[0].size());

    p.addJunk(someJunkAt(130));
    assertEquals(2, p.stacks[0].size());

    p.addJunk(someJunkAt(180));
    assertEquals(1, p.stacks[1].size());

    assertEquals(2, p.stacks[0].getChildren().size);
    assertEquals(1, p.stacks[1].getChildren().size);

  }

  @Test
  public void testHidesWhenFindingThreeTheSame() throws Exception {

    p = new Platform(null, 4);
    p.setX(100);

    assertFalse(p.addJunk(someJunkAt(105)));
    assertFalse(p.addJunk(someJunkAt(105)));
    assertTrue(p.addJunk(someJunkAt(105)));

  }

  @Test
  public void testOverlapping() {

    p = new Platform(null, 4);

    p.setX(x);
    p.setY(50);

    assertFalse("This is above the platform", overlaps(50 + p.getHeight() + 10));

    assertTrue("This is just touching the platform", overlaps(50 + p.getHeight() - 1));

    assertFalse("This is too far below the platform", overlaps(50 + p.getHeight() - BasicJunk.SIZE));

  }

  private boolean overlaps(float y) {
    return p.overlaps(new Rectangle(x, y, 64, 64)) > -1;
  }

  @Test
  public void repositionRocks() {

    p = new Platform(null, 4);
    p.stacks[0].addJunk(new BasicJunk(JunkType.PLAIN_ROCK, null));
    p.stacks[0].addJunk(new BasicJunk(JunkType.PLAIN_ROCK, null));
    assertStackHeight(2, p.stacks[0]);

    p.stacks[0].addJunk(new BasicJunk(JunkType.PLAIN_ROCK, null));

    p.act(2f);
    p.act(1f);

    assertStackHeight(0, p.stacks[0]);

  }

  private void assertStackHeight(int expectedNumberOfBlocks, JunkStack stack) {
    assertEquals((BasicJunk.SIZE * expectedNumberOfBlocks) + p.getHeight(),
                 stack.rectangle.getHeight(), 0.1);
  }

  private BasicJunk someJunkAt(int x) {
    BasicJunk junk = new BasicJunk(JunkType.PLAIN_ROCK, null);
    junk.setX(x);
    return junk;
  }
}
