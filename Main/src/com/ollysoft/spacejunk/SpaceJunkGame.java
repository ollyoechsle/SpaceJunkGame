package com.ollysoft.spacejunk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ollysoft.spacejunk.util.Assets;

public class SpaceJunkGame extends Game {

  public MainMenuScreen mainMenuScreen;
  public GameScreen gameScreen;
  public Screen currentScreen;

  private Assets assets;

  public void create() {
    assets = new Assets();
    mainMenuScreen = new MainMenuScreen(this, assets);
    gameScreen = new GameScreen(this, assets);
    displayMainMenu();
  }

  @Override
  public void dispose() {
    assets.dispose();
    mainMenuScreen.dispose();
    gameScreen.dispose();
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
