package com.ollysoft.spacejunk.objects.scoring;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public interface ScoreModel {

  int getScore();

  void onCollectedScore(Array<BasicJunk> items);

}
