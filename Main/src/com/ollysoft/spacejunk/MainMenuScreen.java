package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ollysoft.spacejunk.objects.props.Stars;
import com.ollysoft.spacejunk.util.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * com.ollysoft.spacejunk
 */
public class MainMenuScreen extends ScreenAdapter {

  private final Stage stage;
  private final Skin uiSkin;
  private final SpaceJunkGame game;
  private final Table table;

  public MainMenuScreen(final SpaceJunkGame game, Assets assets) {
    this.game = game;
    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

    uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

    table = new Table();

    Label appName = new Label("Space Junk!", uiSkin);

    TextButton newGame = new TextButton("New Game", uiSkin);
    newGame.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        table.addAction(
            sequence(
                parallel(
                    fadeOut(0.5f),
                    moveTo(-Gdx.graphics.getWidth()/2, 0, 0.5f)
                ),
                displayGameScreen(game)
            )
        );
      }
    });

    TextButton exit = new TextButton("Exit", uiSkin);
    exit.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });


    stage.addActor(new Stars(assets));
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

  private Action displayGameScreen(final SpaceJunkGame game) {
    return new Action() {
      @Override
      public boolean act(float v) {

        game.displayGameScreen();
        return true;
      }
    };
  }

  @Override
  public void show() {
    table.addAction(parallel(
        fadeIn(0.5f),
        moveTo(0, 0, 0.5f)
    ));
    Gdx.input.setInputProcessor(stage);

  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
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
