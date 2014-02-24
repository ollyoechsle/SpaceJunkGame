package com.ollysoft.spacejunk;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ollysoft.spacejunk.objects.score.ScoreModel;
import com.ollysoft.spacejunk.objects.score.ScoreView;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.MenuScreen;

public class GameOverScreen extends MenuScreen {

  public GameOverScreen(final SpaceJunkGame game, Assets assets, ScoreModel score) {
    super(assets);

    addLabel("Game Over");
    addLabel("" + score.getScore());
    addButton("Play Again", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        transitionOutThen(displayGameScreen(game));
      }
    });
    table.layout();

  }

  private Action displayGameScreen(final SpaceJunkGame game) {
    return new Action() {
      @Override
      public boolean act(float v) {
        game.startNewGame();
        return true;
      }
    };
  }

}
