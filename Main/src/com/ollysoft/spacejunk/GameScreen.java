package com.ollysoft.spacejunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ollysoft.spacejunk.objects.Magnet;

import java.util.Iterator;

/**
 * com.ollysoft.spacejunk
 */
public class GameScreen implements Screen {

  private Texture  dropImage, bucketImage, background;
  private Sound dropSound;
  private Music rainMusic;

  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Magnet magnet;
  private Vector3 touchPos = new Vector3();

  Array<Rectangle> raindrops;
  long lastDropTime;
  private SpaceJunkGame game;

  public GameScreen(SpaceJunkGame game) {
    this.game = game;

    // load the images for the droplet and the magnet, 64x64 pixels each
    background = new Texture(Gdx.files.internal("background-1.png"));
    dropImage = new Texture(Gdx.files.internal("droplet.png"));
    bucketImage = new Texture(Gdx.files.internal("collector.png"));

    // load the drop sound effect and the rain background "music"
    dropSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music-1.mp3"));
    rainMusic.setLooping(true);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 768, 1024);

    batch = new SpriteBatch();

    magnet = new Magnet(bucketImage);
    magnet.x = 800 / 2 - 64 / 2;
    magnet.y = 20;

    raindrops = new Array<Rectangle>();
    spawnRaindrop();

  }

  @Override
  public void show() {
    // start the playback of the background music
    // when the screen is shown
    rainMusic.play();
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
      spawnRaindrop();
    }

    Iterator<Rectangle> iter = raindrops.iterator();
    while (iter.hasNext()) {
      Rectangle raindrop = iter.next();
      raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
      if (raindrop.y + 64 < 0) {
        iter.remove();
      }
      if (raindrop.overlaps(magnet)) {
        dropSound.play();
        iter.remove();
      }

    }
  }

  private void handleInputs() {
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      magnet.x = touchPos.x - 64 / 2;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      magnet.x -= 200 * Gdx.graphics.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      magnet.x += 200 * Gdx.graphics.getDeltaTime();
    }

    if (magnet.x < 0) {
      magnet.x = 0;
    }
    if (magnet.x > 800 - 64) {
      magnet.x = 800 - 64;
    }
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.draw(background, 0, 0);
    magnet.draw(batch);
    batch.draw(bucketImage, magnet.x, magnet.y);

    for (Rectangle raindrop : raindrops) {
      batch.draw(dropImage, raindrop.x, raindrop.y);
    }
    batch.end();
  }

  private void spawnRaindrop() {
    Rectangle raindrop = new Rectangle();
    raindrop.x = MathUtils.random(0, 800 - 64);
    raindrop.y = 480;
    raindrop.width = 64;
    raindrop.height = 64;
    raindrops.add(raindrop);
    lastDropTime = TimeUtils.nanoTime();
  }

  @Override
  public void resize(int width, int height) {
    camera.setToOrtho(false, width, height);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
    dropImage.dispose();
    bucketImage.dispose();
    dropSound.dispose();
    rainMusic.dispose();
    batch.dispose();
  }


}
