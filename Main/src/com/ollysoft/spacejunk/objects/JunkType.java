package com.ollysoft.spacejunk.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.util.Assets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * com.ollysoft.spacejunk.objects
 */
public enum JunkType {

  PLAIN_ROCK {
    @Override
    public TextureRegion getTexture(Assets assets) {
      return assets.rock1;
    }

    @Override
    public int getScore() {
      return 10;
    }
  },

  RED_ROCK {
    @Override
    public TextureRegion getTexture(Assets assets) {
      return assets.rock2;
    }

    @Override
    public int getScore() {
      return 50;
    }
  };

  public abstract TextureRegion getTexture(Assets assets);

  public abstract int getScore();

  private static final
  List<JunkType>
      VALUES =
      Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  public static JunkType randomJunkType() {
    return VALUES.get(RANDOM.nextInt(SIZE));
  }

}
