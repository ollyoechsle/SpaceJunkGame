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
    this.currentScreen = screen;
    super.setScreen(screen);
  }

  public void displayMainMenu() {
    System.out.println("Setting display main menu");
    this.setScreen(mainMenuScreen);
  }

  public void displayGameScreen() {
    System.out.println("Setting display game screen");
    this.setScreen(gameScreen);
  }

}
