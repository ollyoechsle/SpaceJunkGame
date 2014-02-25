package com.ollysoft.spacejunk.objects.junk;

import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.util.RelativePosition;

public class FallingJunk extends BasicJunk {

  public static final int FALL_SPEED = 128;
  public static int SIZE = BasicJunk.SIZE;
  private final GameScreen game;

  public FallingJunk(JunkType type, GameScreen game) {
    super(type, type.getTexture(game.assets));
    this.game = game;
  }

  @Override
  public void act(float delta) {
    float y = getY() - (FALL_SPEED * delta);
    if (y + SIZE < 0) {
      //game.score.onMissedBlock(this);
      remove();
    } else {
      setY(y);
    }
    RelativePosition position = game.platform.getRelativePosition(this.getBoundingBox());
    if (game.platform.canLandOn(position)) {
      this.remove();
      game.platform.addJunk(this, position);
      //game.assets.dropSound.play();
    }
  }

}
