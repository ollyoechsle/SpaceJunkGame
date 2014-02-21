package com.ollysoft.spacejunk.objects.platform;

import com.ollysoft.spacejunk.objects.junk.BasicJunk;

public interface MoundListener {

  public void onObjectRemoved(BasicJunk junk, int dx, int dy);

  public void onObjectFallenFromMound(BasicJunk junk, int dx, int dy);

}
