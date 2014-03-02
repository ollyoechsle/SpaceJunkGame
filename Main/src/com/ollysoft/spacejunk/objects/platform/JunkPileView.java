package com.ollysoft.spacejunk.objects.platform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ollysoft.spacejunk.GameScreen;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class JunkPileView extends Group implements JunkPileListener {

  private Platform platform;

  public JunkPileView(Platform platform) {
    this.platform = platform;
    this.setTransform(false);
  }

  @Override
  public void onObjectAdded(BasicJunk newJunk, BasicJunk fallenJunk, int dx, int dy) {
    addActor(newJunk);
    // first position relative to the platform - exactly where it was at the point when it touched
    newJunk.setPosition(fallenJunk.getX() - platform.getX(), fallenJunk.getY() - platform.getY());
    // later, move to the correct position if not exactly in the right place
    newJunk.addAction(moveTo(dx * BasicJunk.SIZE, dy * BasicJunk.SIZE, 0.25f));
  }

  @Override
  public void onObjectRemoved(BasicJunk junk, int dx, int dy) {
    junk.addAction(
        sequence(
            Actions.fadeOut(0.5f),
            Actions.removeActor()
        )
    );
  }

  @Override
  public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy) {
    GameScreen game = platform.game;
    FallingJunk fallingJunk = new FallingJunk(junk.type, game, 128);
    // position relative to the game
    fallingJunk.setPosition(junk.getX() + platform.getX(), junk.getY() + platform.getY());
    game.actionStage.addActor(fallingJunk);
    removeActor(junk);
  }

}
