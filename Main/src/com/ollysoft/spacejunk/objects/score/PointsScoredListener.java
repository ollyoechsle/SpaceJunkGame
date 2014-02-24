package com.ollysoft.spacejunk.objects.score;

import com.badlogic.gdx.utils.Array;
import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public interface PointsScoredListener {

  void onPointsScored(int points, Array<BasicJunk> items);

}
