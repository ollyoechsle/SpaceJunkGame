package com.ollysoft.spacejunk.objects.junk;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ollysoft.spacejunk.util.Assets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
