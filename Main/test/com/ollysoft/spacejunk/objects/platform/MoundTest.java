package com.ollysoft.spacejunk.objects.platform;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoundTest {

  private static final BasicJunk PLAIN_ROCK = new BasicJunk(JunkType.PLAIN_ROCK, null);
  private static final BasicJunk GOLD_ROCK = new BasicJunk(JunkType.GOLD_ROCK, null);

  @Test
  public void getGroups() {
    Mound m = new Mound(2);
    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    Assert.assertEquals(0, m.getGroups().size);

    m.objectAt(2, 0).place(PLAIN_ROCK);
    Assert.assertEquals(1, m.getGroups().size);

    m.objectAt(0, 1).place(GOLD_ROCK);
    m.objectAt(1, 1).place(GOLD_ROCK);
    Assert.assertEquals(1, m.getGroups().size);

    m.objectAt(2, 1).place(GOLD_ROCK);
    System.out.println(m.getGroups().size);
    Assert.assertEquals(2, m.getGroups().size);

  }

  @Test
  public void gridIsInitialised() throws Exception {
    Mound m = new Mound(1);
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
    Mound m = new Mound(1);
    Mound.MoundObject moundObject = m.objectAt(0, 0);
    moundObject.place(PLAIN_ROCK);
    assertFalse(moundObject.empty);
  }

  @Test
  public void testMoundsTheSame() {
    Mound m = new Mound(1);
    assertFalse(m.objectAt(0, 0).isSame(m.objectAt(1, 0)));

    m.objectAt(0, 0).place(PLAIN_ROCK);
    m.objectAt(1, 0).place(PLAIN_ROCK);
    assertTrue(m.objectAt(0, 0).isSame(m.objectAt(1, 0)));

    m.objectAt(0, 1).place(GOLD_ROCK);
    assertFalse(m.objectAt(0, 0).isSame(m.objectAt(0, 1)));

  }

}
