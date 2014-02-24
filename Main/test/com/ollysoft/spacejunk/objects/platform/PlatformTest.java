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
  private BasicJunk fallingJunk;
  private RelativePosition position;

  @Test
  public void hasCorrectActors() {
    givenPlatform();
    assertEquals("Should consist of the paddle and the junk group", 2, p.getChildren().size);
  }

  @Test
  public void testAddJunk() throws Exception {

    givenPlatformAt(100);
    givenFallingJunkAt(100);

    int previousPileSize = pileSize();

    assertFalse("Doesn't overlap", landed(fallingJunk, BasicJunk.SIZE));

    assertTrue("Does overlap", landed(fallingJunk, 0));

    p.addJunk(fallingJunk, position);

    assertEquals(previousPileSize + 1, pileSize());
    assertEquals(0, lastObjectAddedToPile().getX(), 0.1d);
    assertEquals(BasicJunk.SIZE, lastObjectAddedToPile().getY(), 0.1d);

  }

  @Test
  public void itemsPassingByJustToTheSideDoNotLand() throws Exception {

    givenPlatformAt(100);

    givenFallingJunkAt(100);
    assertTrue("Can land", landed(fallingJunk, 0));

    givenFallingJunkAt(100 - BasicJunk.SIZE);
    assertFalse("Should pass by on the left side", landed(fallingJunk, 0));

  }

  private void givenPlatformAt(int x1) {
    givenPlatform();
    p.setX(x1);
  }

  private void givenFallingJunkAt(int x1) {
    fallingJunk = new BasicJunk(JunkType.randomJunkType(), null);
    fallingJunk.setX(x1);
  }

  private boolean landed(BasicJunk fallingJunk, int heightAbovePlatform) {
    fallingJunk.setY(p.getY() + p.getHeight() + heightAbovePlatform);
    position = p.getRelativePosition(fallingJunk.getBoundingBox());
    return p.canLandOn(position);
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

    givenPlatform();
    p.setX(100);

   /* assertFalse(p.addJunk(someJunkAt(105), 0));
    assertFalse(p.addJunk(someJunkAt(105), 0));
    assertTrue(p.addJunk(someJunkAt(105), 0));*/

  }

  private void givenPlatform() {
    p = new Platform(null, null, null, null);
  }

}
