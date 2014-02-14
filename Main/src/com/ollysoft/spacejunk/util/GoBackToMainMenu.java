package com.ollysoft.spacejunk.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ollysoft.spacejunk.SpaceJunkGame;

/**
 * com.ollysoft.spacejunk.util
 */
public class GoBackToMainMenu extends InputAdapter {

  private SpaceJunkGame game;

  public GoBackToMainMenu(SpaceJunkGame game) {
    this.game = game;
    Gdx.input.setCatchBackKey(true);
  }

  @Override
  public boolean keyDown(int keycode) {

    if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
      if (game.currentScreen == game.gameScreen) {
        game.displayMainMenu();
        return false;
      }
    }

    return true;

  }

}
