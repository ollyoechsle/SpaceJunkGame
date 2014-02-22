package com.ollysoft.spacejunk.objects.platform;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public interface MoundListener {

  public void onObjectAdded(BasicJunk junk, BasicJunk fallenJunk, int dx, int dy);

  public void onObjectRemoved(BasicJunk junk, int dx, int dy);

  public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy);

}
