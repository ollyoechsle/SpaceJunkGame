package com.ollysoft.spacejunk.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ollysoft.spacejunk.SpaceJunkGame;

/**
 * com.ollysoft.spacejunk.util
 */
public class GameInputHandler extends InputAdapter {

  private final SpaceJunkGame game;
  private final MovementListener listener;

  public GameInputHandler(SpaceJunkGame game, MovementListener listener) {
    this.game = game;
    this.listener = listener;
    Gdx.input.setCatchBackKey(true);
  }

  @Override
  public boolean keyDown(int keycode) {
    if (game.currentScreen == game.gameScreen) {

      if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
        game.displayMainMenu();
        return true;
      }

      if (keycode == Input.Keys.LEFT) {
        listener.moveLeft();
        return true;
      }

      if (keycode == Input.Keys.RIGHT) {
        listener.moveRight();
        return true;
      }

    }

    return false;

  }

}
