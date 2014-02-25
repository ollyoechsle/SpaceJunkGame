package com.ollysoft.spacejunk.objects.junk;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.ollysoft.spacejunk.util.Assets;

import java.util.Random;

public enum JunkType {

  PLAIN_ROCK {
    @Override
    public TextureRegion getTexture(Assets assets) {
      return assets.plainRock;
    }

    @Override
    public int getCollectionScore() {
      return 10;
    }

    @Override
    public int getMissCost() {
      return 10;
    }
  },

  RED_ROCK {
    @Override
    public TextureRegion getTexture(Assets assets) {
      return assets.redRock;
    }

    @Override
    public int getCollectionScore() {
      return -50;
    }

    @Override
    public int getMissCost() {
      return 0;
    }
  },

  GOLD_ROCK {
    @Override
    public TextureRegion getTexture(Assets assets) {
      return assets.goldRock;
    }

    @Override
    public int getCollectionScore() {
      return 30;
    }

    @Override
    public int getMissCost() {
      return 30;
    }
  };

  public abstract TextureRegion getTexture(Assets assets);

  public abstract int getCollectionScore();

  public abstract int getMissCost();

  private static final Random RANDOM = new Random();

  public static JunkType randomJunkType() {
    float x = MathUtils.random(0, 1f);
    if (x < 0.5) {
      return PLAIN_ROCK;
    }
    if (x < 0.8) {
      return GOLD_ROCK;
    }
    return RED_ROCK;
  }

}
