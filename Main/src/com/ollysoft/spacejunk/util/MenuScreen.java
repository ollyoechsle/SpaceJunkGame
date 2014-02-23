package com.ollysoft.spacejunk.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ollysoft.spacejunk.objects.props.Stars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public abstract class MenuScreen extends ScreenAdapter {

  protected final Stage stage;
  protected final Table table;
  private final Assets assets;

  public MenuScreen(Assets assets) {
    this.assets = assets;
    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    table = new Table();
    stage.addActor(new Stars(assets));
    stage.addActor(table);
    table.setFillParent(true);

  }

  protected void transitionOutThen(Action thenAction) {
    table.addAction(
        sequence(
            parallel(
                fadeOut(0.5f),
                moveTo(-Gdx.graphics.getWidth() / 2, 0, 0.5f)
            ),
            thenAction
        )
    );
  }

  protected void addButton(String text, ClickListener listener) {
    TextButton button = new TextButton(text, assets.uiSkin);
    button.addListener(listener);
    addButton(button);
  }

  protected void addButton(TextButton button) {
    table.add(button).width(400f).height(100f).spaceBottom(10);
    table.row();
  }

  protected void addLabel(String text) {
    Label label = new Label(text, assets.uiSkin);
    table.add(label).width(100f).spaceBottom(100);
    table.row();
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

  public void dispose() {
    stage.dispose();
  }

}
