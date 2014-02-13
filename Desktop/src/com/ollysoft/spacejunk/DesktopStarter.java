package com.ollysoft.spacejunk;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * com.ollysoft.spacejunk
 */
public class DesktopStarter {

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "Space Junk!";
    cfg.useGL20 = true;
    cfg.width = 800;
    cfg.height = 480;
    new LwjglApplication(new SpaceJunkGame(), cfg);
  }

}
