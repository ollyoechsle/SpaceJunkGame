package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;

/**
 * com.ollysoft.spacejunk
 */
public class MainMenuScreen extends ScreenAdapter {

  private Stage stage;
  private final Skin uiSkin;

  public MainMenuScreen(final SpaceJunkGame game) {
    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

    uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

    Label appName = new Label("Space Junk!", uiSkin);

    TextButton newGame = new TextButton("New Game", uiSkin);
    newGame.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        new Logger("SpaceJunk").debug("Display game screen");
        game.displayGameScreen();
        return true;
      }
    });

    TextButton exit = new TextButton("Exit", uiSkin);
    exit.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        new Logger("SpaceJunk").debug("Exit game");
        Gdx.app.exit();
        return true;
      }
    });

    Table table = new Table();
    stage.addActor(table);

    table.setFillParent(true);

    table.add(appName).width(100f).spaceBottom(100);
    table.row();

    table.add(newGame).width(400f).height(100f).spaceBottom(10);
    table.row();

    table.add(exit).width(400f).height(100f).spaceBottom(10);
    table.row();

    table.layout();

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  public void resize(int width, int height) {
    stage.setViewport(width, height, true);
  }

  public void render(float v) {
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
  }

  public void dispose() {
    uiSkin.dispose();
    stage.dispose();
  }

}
