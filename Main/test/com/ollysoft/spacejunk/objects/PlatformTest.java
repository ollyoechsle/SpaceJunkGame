package com.ollysoft.spacejunk.objects;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlatformTest {

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

  private BasicJunk someJunkAt(int x) {
    BasicJunk junk = new BasicJunk(JunkType.PLAIN_ROCK, null);
    junk.setX(x);
    return junk;
  }
}
