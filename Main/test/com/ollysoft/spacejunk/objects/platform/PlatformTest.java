package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.util.RelativePosition;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {

  private Platform p;
  private int x = 100;

  @Test
  public void hasCorrectActors() {
    Platform p = new Platform(null, 4, null, null);
    assertEquals("Should consist of the paddle and the junk group", 2, p.getChildren().size);
  }

  @Test
  public void testAddJunk() throws Exception {

    p = new Platform(null, 4, null, null);
    p.setX(100);
    int previousPileSize = pileSize();

    BasicJunk fallingJunk = new BasicJunk(JunkType.randomJunkType(), null);
    fallingJunk.setX(100);

    RelativePosition position;

    fallingJunk.setY(p.getY() + p.getHeight() + BasicJunk.SIZE);
    position = p.getRelativePosition(fallingJunk.getBoundingBox());
    assertFalse("Doesn't overlap", p.canLandOn(position));

    fallingJunk.setY(p.getY() + p.getHeight());
    position = p.getRelativePosition(fallingJunk.getBoundingBox());
    assertTrue("Does overlap", p.canLandOn(position));

    p.addJunk(fallingJunk, position);

    assertEquals(previousPileSize + 1, pileSize());
    assertEquals(0, lastObjectAddedToPile().getX(), 0.1d);
    assertEquals(BasicJunk.SIZE, lastObjectAddedToPile().getY(), 0.1d);

  }

  private int pileSize() {
    return p.junkPileView.getChildren().size;
  }

  private Actor lastObjectAddedToPile() {
    SnapshotArray<Actor> children = p.junkPileView.getChildren();
    return children.get(children.size - 1);
  }

  @Test
  public void testHidesWhenFindingThreeTheSame() throws Exception {

    p = new Platform(null, 4, null, null);
    p.setX(100);

   /* assertFalse(p.addJunk(someJunkAt(105), 0));
    assertFalse(p.addJunk(someJunkAt(105), 0));
    assertTrue(p.addJunk(someJunkAt(105), 0));*/

  }

}
