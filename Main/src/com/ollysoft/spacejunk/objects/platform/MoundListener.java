package com.ollysoft.spacejunk.objects.platform;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public interface MoundListener {

  public void onObjectRemoved(BasicJunk junk, int x, int y);

  public void onObjectFallenFromMound(BasicJunk junk, int x, int y);

}
