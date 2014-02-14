package com.ollysoft.spacejunk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceJunkGame extends Game {

  public MainMenuScreen mainMenuScreen;
  public GameScreen gameScreen;
  public Screen currentScreen;

  public void create() {
    mainMenuScreen = new MainMenuScreen(this);
    gameScreen = new GameScreen(this);

    displayMainMenu();
  }

  @Override
  public void setScreen(Screen screen) {
    super.setScreen(screen);
    this.currentScreen = screen;
  }

  public void displayMainMenu() {
    this.setScreen(mainMenuScreen);
  }

  public void displayGameScreen() {
    this.setScreen(gameScreen);
  }

}
