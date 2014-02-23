package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.ollysoft.spacejunk.objects.props.Stars;
import com.ollysoft.spacejunk.objects.score.*;
import com.ollysoft.spacejunk.util.Assets;
import com.ollysoft.spacejunk.util.GameState;

public class GameScreen extends ScreenAdapter implements PointsScoredListener, FuelTankListener, MovementListener {

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

  public GameScreen(SpaceJunkGame game, Assets assets) {
    this.game = game;
    this.state = GameState.LOADING;
    this.assets = assets;

    // load the images for the droplet and the platform, 64x64 pixels each


    score = new BasicScoreModel(0, this);
    FuelTankModel fuelTank = new BasicFuelTankModel(750, this);

    // load the drop sound effect and the rain starsBackground "music"


    camera = new OrthographicCamera();
    camera.setToOrtho(false, 768, 1280);

    platform = new Platform(new TextureRegion(assets.magnetImage), 4, this, score, fuelTank);

    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    stage.addActor(new Stars(assets));
    stage.addActor(platform);

    hud = new Table().top().right();
    hud.setFillParent(true);

    ScoreView scoreView = new ScoreView(assets, score);
    hud.add(scoreView).width(150).right().height(50).spaceBottom(10);
    hud.row();

    FuelTankView fuelTankView = new FuelTankView(assets, fuelTank);
    hud.add(fuelTankView).width(150).right().height(50).spaceBottom(10);
    hud.row();

    stage.addActor(hud);

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
      platform.moveX(-BasicJunk.SIZE);
    }
  }

  @Override
  public void moveRight() {
    if (state.canMove()) {
      platform.moveX(BasicJunk.SIZE);
    }
  }

  private void animate() {
    if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
      spawnJunk();
    }
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

    FallingJunk block = new FallingJunk(JunkType.randomJunkType(), this);
    float x = MathUtils.random(0, Gdx.graphics.getWidth() - BasicJunk.SIZE);
    x = (int) (x / BasicJunk.SIZE);
    x *= BasicJunk.SIZE;

    block.setPosition(x, Gdx.graphics.getHeight());
    lastDropTime = TimeUtils.nanoTime();
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
}
