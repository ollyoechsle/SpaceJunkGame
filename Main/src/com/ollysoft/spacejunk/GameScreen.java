package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ollysoft.spacejunk.input.GameInputHandler;
import com.ollysoft.spacejunk.input.MovementListener;
import com.ollysoft.spacejunk.objects.fuel.BasicFuelTankModel;
import com.ollysoft.spacejunk.objects.fuel.FuelTankListener;
import com.ollysoft.spacejunk.objects.fuel.FuelTankModel;
import com.ollysoft.spacejunk.objects.fuel.FuelTankView;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;
import com.ollysoft.spacejunk.objects.junk.FallingJunk;
import com.ollysoft.spacejunk.objects.junk.JunkType;
import com.ollysoft.spacejunk.objects.platform.Platform;
import com.ollysoft.spacejunk.objects.props.Boulder;
import com.ollysoft.spacejunk.objects.props.Stars;
import com.ollysoft.spacejunk.objects.score.*;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.GameState;

public class GameScreen extends ScreenAdapter implements PointsScoredListener, FuelTankListener, MovementListener {

  private static final boolean BOULDERS_ENABLED = false;
  public static final int FASTEST = 1000000000 * 2;
  public final Assets assets;

  private GameState state;

  private OrthographicCamera camera;

  public final Platform platform;
  private Vector3 touchPos = new Vector3();

  private long lastDropTime;
  private final SpaceJunkGame game;
  public final Stage stage;
  public final ScoreModel score;
  private final Table hud;
  private int fallSpeed = 64;

  public GameScreen(SpaceJunkGame game, Assets assets) {
    this.game = game;
    this.state = GameState.LOADING;
    this.assets = assets;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 768, 1280);

    score = new BasicScoreModel(0, this);
    FuelTankModel fuelTank = new BasicFuelTankModel(750, this);

    platform = new Platform(assets, this, score, fuelTank);

    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    stage.addActor(new Stars(assets));
    if (BOULDERS_ENABLED) {
      addBoulders(assets);
    }
    stage.addActor(platform);

    hud = new Table().top().right();
    hud.setFillParent(true);

    ScoreView scoreView = new ScoreView(assets, score);
    hud.add(scoreView).width(250).right().height(50).spaceBottom(10).spaceRight(10);
    hud.row();

    FuelTankView fuelTankView = new FuelTankView(assets, fuelTank);
    hud.add(fuelTankView).width(250).right().height(50).spaceBottom(10).spaceRight(10);
    hud.row();

    stage.addActor(hud);

  }

  private void addBoulders(Assets assets) {
    for (int i = 0; i < 10; i++) {
      int x = (MathUtils.random(-100, Gdx.graphics.getWidth()));
      int speed = i * 5;
      stage.addActor(new Boulder(assets, x, speed));
    }
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GameInputHandler(game, this)));
    // start the playback of the starsBackground music
    // when the screen is shown
    assets.music.play();
    state = GameState.PLAYING;
  }

  @Override
  public void hide() {
    assets.music.pause();
    state = GameState.PAUSED;
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void onFuelTankEmpty() {
    assets.music.pause();
    assets.crashSound.play();
    game.setScreen(new GameOverScreen(game, assets, score));
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    if (state.canMove()) {
      stage.act(Gdx.graphics.getDeltaTime());
    }
    stage.draw();

    handleInputs();

    animate();

  }

  public void togglePaused() {
    if (state == GameState.PAUSED) {
      state = GameState.PLAYING;
    } else if (state == GameState.PLAYING) {
      state = GameState.PAUSED;
    }
  }

  @Override
  public void moveLeft() {
    if (state.canMove()) {
      platform.moveX(-BasicJunk.SIZE * 3);
    }
  }

  @Override
  public void moveRight() {
    if (state.canMove()) {
      platform.moveX(BasicJunk.SIZE * 3);
    }
  }

  private void animate() {
    if (TimeUtils.nanoTime() - lastDropTime > interarrivalTime()) {
      spawnJunk();
    }
  }

  private int interarrivalTime() {
    return FASTEST;
  }

  private void handleInputs() {
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      platform.moveTo(touchPos.x);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      //platform.moveX(-KEYBOARD_MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      //platform.moveX(KEYBOARD_MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }

  }

  private void spawnJunk() {
    if (!state.canMove()) {
      return;
    }

    float x = MathUtils.random(0, Gdx.graphics.getWidth() - BasicJunk.SIZE);
    x = (x / BasicJunk.SIZE);

    addJunk((int) x, 0);

    float doubleX = MathUtils.random(0, 1);
    float doubleY = MathUtils.random(0, 1);
    if (doubleX > 0.8) {
      addJunk((int) x + 1, 0);
      if (doubleX > 0.99) {
        addJunk((int) x + 2, 0);
      }
    } else {
      if (doubleY > 0.8) {
        addJunk((int) x, -1);
      }
    }

    lastDropTime = TimeUtils.nanoTime();
  }

  private void addJunk(int x, int y) {
    FallingJunk block = new FallingJunk(JunkType.randomJunkType(), this, getFallSpeed());
    block.setPosition(x * BasicJunk.SIZE, Gdx.graphics.getHeight() + (y * BasicJunk.SIZE));
    stage.addActor(block);
  }

  public static int width, height;

  @Override
  public void resize(int width, int height) {
    GameScreen.width = width;
    GameScreen.height = height;
    stage.setViewport(width, height, true);
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  public void onPointsScored(int points, Array<BasicJunk> items) {
    float sumX = 0, sumY = 0;
    for (BasicJunk item : items) {
      sumX += item.getX();
      sumY += item.getY();
    }
    stage.addActor(new PointsLabel(assets, platform.getX() + (sumX / items.size), platform.getY() + (sumY / items.size), "" + points));
    assets.scoreSound.play();
  }

  public int getFallSpeed() {
    if (fallSpeed < 196) {
      fallSpeed++;
    }
    return fallSpeed;
  }
}
