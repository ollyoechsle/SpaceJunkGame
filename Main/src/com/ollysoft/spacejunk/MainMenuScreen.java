package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.MenuScreen;

public class MainMenuScreen extends MenuScreen {

  private SpaceJunkGame game;

  public MainMenuScreen(final SpaceJunkGame game, Assets assets) {
    super(assets);
    this.game = game;
  }

  @Override
  public void initUI(Assets assets) {
    addLabel("Space Junk!");
    addButton("New Game", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        transitionOutThen(displayGameScreen(game));
      }
    });
    addButton("Exit", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });
  }

  private Action displayGameScreen(final SpaceJunkGame game) {
    return new Action() {
      @Override
      public boolean act(float v) {
        game.displayGameScreen();
        return true;
      }
    };
  }

}
