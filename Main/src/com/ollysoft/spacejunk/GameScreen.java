package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.ollysoft.spacejunk.objects.Block;
import com.ollysoft.spacejunk.objects.Magnet;
import com.ollysoft.spacejunk.util.GoBackToMainMenu;

/**
 * com.ollysoft.spacejunk
 */
public class GameScreen implements Screen {

  private Texture blockImage, bucketImage, background;
  private Sound dropSound;
  private Music music;

  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Magnet magnet;
  private Vector3 touchPos = new Vector3();

  Array<Rectangle> raindrops;
  long lastDropTime;
  private SpaceJunkGame game;
  private Stage stage;

  public GameScreen(SpaceJunkGame game) {
    this.game = game;

    // load the images for the droplet and the magnet, 64x64 pixels each
    background = new Texture(Gdx.files.internal("background-1.png"));
    blockImage = new Texture(Gdx.files.internal("block.png"));
    bucketImage = new Texture(Gdx.files.internal("collector.png"));

    // load the drop sound effect and the rain background "music"
    dropSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
    music = Gdx.audio.newMusic(Gdx.files.internal("music-1.mp3"));
    music.setLooping(true);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 768, 1280);

    batch = new SpriteBatch();

    magnet = new Magnet(bucketImage);
    magnet.setX(800 / 2 - 64 / 2);
    magnet.setY(20);

    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    stage.addActor(magnet);

    Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GoBackToMainMenu(game)));

    raindrops = new Array<Rectangle>();
    spawnBlock();

  }

  @Override
  public void show() {
    // start the playback of the background music
    // when the screen is shown
    music.play();
  }

  @Override
  public void hide() {
  }

  @Override
  public void render(float delta) {

    draw();

    handleInputs();

    animate();

  }

  private void animate() {
    if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
      spawnBlock();
    }

    stage.act();
  }

  private void handleInputs() {
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      magnet.setPosition(touchPos.x - 64 / 2, touchPos.y - 64 / 2);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      magnet.moveX(-200 * Gdx.graphics.getDeltaTime());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      magnet.moveX(200 * Gdx.graphics.getDeltaTime());
    }

    if (magnet.getX() < 0) {
      magnet.setX(0);
    }
    if (magnet.getX() > 800 - 64) {
      magnet.setX(800 - 64);
    }
  }

  private void draw() {
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    batch.begin();
    drawBackground();
    batch.end();

    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();

  }

  private void drawBackground() {
    batch.draw(background, 0, 0);
  }

  private void spawnBlock() {
    Block block = new Block(blockImage);
    block.setPosition(MathUtils.random(0, 800 - 64), 480);
    lastDropTime = TimeUtils.nanoTime();
    stage.addActor(block);
  }

  @Override
  public void resize(int width, int height) {
    stage.setViewport(width, height, true);
  }

  @Override
  public void pause() {
    music.pause();
  }

  @Override
  public void resume() {
    music.play();
  }

  @Override
  public void dispose() {
    blockImage.dispose();
    bucketImage.dispose();
    dropSound.dispose();
    music.dispose();
    batch.dispose();
    stage.dispose();
  }

}
