package com.ollysoft.spacejunk;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SpaceJunkAndroidStarter extends AndroidApplication {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
    cfg.useAccelerometer = false; // saves battery
    cfg.useCompass = false;
    // cfg.useWakelock = true;
    cfg.useGL20 = true;
    initialize(new SpaceJunkGame(), cfg);
  }

}
