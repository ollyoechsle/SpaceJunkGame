package com.ollysoft.spacejunk.objects.junk;

import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.RelativePosition;

/**
 * com.ollysoft.spacejunk.objects
 */
public class FallingJunk extends BasicJunk {

  public static int SIZE = BasicJunk.SIZE;
  private GameScreen game;

  public FallingJunk(JunkType type, GameScreen game) {
    super(type, type.getTexture(game.assets));
    this.game = game;
  }

  @Override
  public void act(float delta) {
    float y = getY() - (200 * delta);
    if (y + SIZE < 0) {
      //game.crashSound.play();
      //game.score.onMissedBlock(this);
      remove();
    } else {
      setY(y);
    }
    RelativePosition position = game.platform.getRelativePosition(this.getBoundingBox());
    if (game.platform.canLandOn(position)) {
      this.remove();
      game.platform.addJunk(this, position);
      game.dropSound.play();
    }
  }

}
