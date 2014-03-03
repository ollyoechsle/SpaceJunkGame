package com.ollysoft.spacejunk.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.ollysoft.spacejunk.SpaceJunkGame;

import static com.badlogic.gdx.Input.Keys;
import static com.ollysoft.spacejunk.util.Movement.*;

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

      if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
        game.displayMainMenu();
        return true;
      }

      if (keycode == Keys.P) {
        listener.togglePaused();
        return true;
      }

      if (keycode == Keys.LEFT) {
        listener.move(LEFT);
        return true;
      }

      if (keycode == Keys.RIGHT) {
        listener.move(RIGHT);
        return true;
      }

      if (keycode == Keys.UP) {
        listener.move(UP);
        return true;
      }

      if (keycode == Keys.DOWN) {
        listener.move(DOWN);
        return true;
      }

      if (keycode == Keys.A) {
        listener.zoomIn();
        return true;
      }

      if (keycode == Keys.Z) {
        listener.zoomOut();
        return true;
      }

    }

    return false;

  }

}
