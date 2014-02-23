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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

  public final Texture magnetImage;
  public final Sound dropSound;
  public final Music music;
  public final Sound crashSound, scoreSound;
  public final Assets assets;

  private GameState state;

  private OrthographicCamera camera;
  private SpriteBatch batch;

  public final Platform platform;
  private Vector3 touchPos = new Vector3();

  long lastDropTime;
  private SpaceJunkGame game;
  public Stage stage;
  public final ScoreModel score;

  public GameScreen(SpaceJunkGame game, Assets assets) {
    this.game = game;
    this.state = GameState.LOADING;
    this.assets = assets;

    // load the images for the droplet and the platform, 64x64 pixels each

    magnetImage = new Texture(Gdx.files.internal("collector.png"));

    score = new BasicScoreModel(0, this);
    FuelTankModel fuelTank = new BasicFuelTankModel(1000, this);

    // load the drop sound effect and the rain starsBackground "music"
    dropSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
    scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
    crashSound = Gdx.audio.newSound(Gdx.files.internal("crash.wav"));
    music = Gdx.audio.newMusic(Gdx.files.internal("music-2.mp3"));
    music.setLooping(true);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 768, 1280);

    batch = new SpriteBatch();

    platform = new Platform(new TextureRegion(magnetImage), 4, this, score, fuelTank);

    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    stage.addActor(new Stars(assets));
    stage.addActor(platform);
    stage.addActor(new ScoreView(assets, score));
    stage.addActor(new FuelTankView(assets, fuelTank));

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GameInputHandler(game, this)));
    // start the playback of the starsBackground music
    // when the screen is shown
    music.play();
    state = GameState.PLAYING;
  }

  @Override
  public void hide() {
    music.pause();
    state = GameState.PAUSED;
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void onFuelTankEmpty() {
    music.pause();
    crashSound.play();
    state = GameState.OVER;
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
    magnetImage.dispose();
    dropSound.dispose();
    music.dispose();
    batch.dispose();
    stage.dispose();
  }

  public void onPointsScored(int points, Array<BasicJunk> items) {
    float sumX = 0, sumY = 0;
    for (BasicJunk item : items) {
      sumX += item.getX();
      sumY += item.getY();
    }
    stage.addActor(new PointsLabel(assets, platform.getX() + (sumX / items.size), platform.getY() + (sumY / items.size), "" + points));
    scoreSound.play();
  }
}
