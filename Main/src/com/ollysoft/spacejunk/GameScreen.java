package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
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
import com.ollysoft.spacejunk.objects.props.ParallaxBackground;
import com.ollysoft.spacejunk.objects.props.Stars;
import com.ollysoft.spacejunk.objects.score.*;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.GameState;
import com.ollysoft.spacejunk.util.Movement;

public class GameScreen extends ScreenAdapter implements PointsScoredListener, FuelTankListener, MovementListener {

  public static final int VELOCITY = BasicJunk.SIZE * 3;
  public final Assets assets;
  private final Stars stars;
  private final SpriteBatch batch;

  private GameState state;

  private OrthographicCamera actionStageCamera;
  private OrthographicCamera fixedStageCamera;

  public final Platform platform;
  private Vector3 touchPos = new Vector3();

  private final SpaceJunkGame game;
  public final Stage actionStage;
  public final Stage fixedStage;

  public final ScoreModel score;
  private final Table hud;
  private int fallSpeed = 64;

  public GameScreen(SpaceJunkGame game, Assets assets) {
    this.game = game;
    this.state = GameState.LOADING;
    this.assets = assets;

    actionStageCamera = new OrthographicCamera();
    actionStageCamera.setToOrtho(false, 768, 1280);

    fixedStageCamera = new OrthographicCamera();
    fixedStageCamera.setToOrtho(false, 768, 1280);

    score = new BasicScoreModel(0, this);
    FuelTankModel fuelTank = new BasicFuelTankModel(750, this);

    platform = new Platform(assets, this, score, fuelTank);

    actionStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    actionStage.setCamera(actionStageCamera);
    actionStage.addActor(new ParallaxBackground(assets, platform, 30));
    actionStage.addActor(platform);

    batch = new SpriteBatch();
    fixedStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    fixedStage.setCamera(fixedStageCamera);
    stars = new Stars(assets, platform);

    hud = new Table().top().right();
    hud.setFillParent(true);

    ScoreView scoreView = new ScoreView(assets, score);
    hud.add(scoreView).width(250).right().height(50).spaceBottom(10).spaceRight(10);
    hud.row();

    FuelTankView fuelTankView = new FuelTankView(assets, fuelTank);
    hud.add(fuelTankView).width(250).right().height(50).spaceBottom(10).spaceRight(10);
    hud.row();
    fixedStage.addActor(hud);

    createLevel();

  }


  @Override
  public void show() {
    Gdx.input.setInputProcessor(new InputMultiplexer(actionStage, new GameInputHandler(game, this)));
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
      hud.act(delta);
      stars.act(delta);
      actionStage.act(Gdx.graphics.getDeltaTime());
    }

    currentZoom += (desiredZoom - currentZoom) * (0.5 * delta);
    actionStage.setViewport(Gdx.graphics.getWidth() * currentZoom, Gdx.graphics.getHeight() * currentZoom, true);

    batch.begin();
    stars.draw(batch, 1);
    batch.end();

    Vector3 position = actionStageCamera.position;
    position.x = platform.getX() + (platform.getWidth() / 2);
    position.y = platform.getY() + (platform.getHeight() / 2);
    actionStage.draw();

    fixedStage.draw();

    handleInputs();


  }

  @Override
  public void zoomIn() {
    desiredZoom = Math.max(1f, desiredZoom - 0.1f);
  }

  @Override
  public void zoomOut() {
    desiredZoom = Math.min(2f, desiredZoom + 0.1f);
  }

  public void togglePaused() {
    if (state == GameState.PAUSED) {
      state = GameState.PLAYING;
    } else if (state == GameState.PLAYING) {
      state = GameState.PAUSED;
    }
  }

  @Override
  public void move(Movement movement) {
    if (state.canMove()) {
      platform.moveX(movement, VELOCITY);
    }
  }

  private void createLevel() {
    for (int i = 0; i < 50; i++) {
      spawnJunk();
    }
  }

  private void handleInputs() {
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      actionStageCamera.unproject(touchPos);
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

    float x = MathUtils.random(-20, +20);
    float y = MathUtils.random(-20, +20);

    addJunk((int) x, (int) y);

  }

  private void addJunk(int x, int y) {
    FallingJunk block = new FallingJunk(JunkType.randomJunkType(), this, getFallSpeed());
    block.setPosition(x * BasicJunk.SIZE, y * BasicJunk.SIZE);
    actionStage.addActor(block);
  }

  public static int width, height;

  @Override
  public void resize(int width, int height) {
    GameScreen.width = width;
    GameScreen.height = height;
    actionStage.setViewport(width * currentZoom, height * currentZoom, true);
    fixedStage.setViewport(width, height, true);
  }

  float currentZoom = 1;
  float desiredZoom = 2;

  @Override
  public void dispose() {
    actionStage.dispose();
  }

  public void onPointsScored(int points, Array<BasicJunk> items) {
    float sumX = 0, sumY = 0;
    for (BasicJunk item : items) {
      sumX += item.getX();
      sumY += item.getY();
    }
    actionStage.addActor(new PointsLabel(assets, platform.getX() + (sumX / items.size), platform.getY() + (sumY / items.size), "" + points));
    assets.scoreSound.play();
  }

  public int getFallSpeed() {
    if (fallSpeed < 196) {
      fallSpeed++;
    }
    return fallSpeed;
  }
}
