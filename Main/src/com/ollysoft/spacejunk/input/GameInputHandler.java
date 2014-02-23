package com.ollysoft.spacejunk.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ollysoft.spacejunk.SpaceJunkGame;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.platform.Platform;

/**
 * com.ollysoft.spacejunk.util
 */
public class GameInputHandler extends InputAdapter {

  private final SpaceJunkGame game;
  private final Platform platform;

  public GameInputHandler(SpaceJunkGame game, Platform platform) {
    this.game = game;
    this.platform = platform;
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
        platform.moveX(-BasicJunk.SIZE);
        return true;
      }

      if (keycode == Input.Keys.RIGHT) {
        platform.moveX(+BasicJunk.SIZE);
        return true;
      }

    }

    return false;

  }

}
