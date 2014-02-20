package com.ollysoft.spacejunk.objects.platform;

import org.junit.Assert;
import org.junit.Test;

/**
 * com.ollysoft.spacejunk.objects.platform
 */
public class MoundTest {

  @Test
  public void testObjectAt() throws Exception {
    Mound m = new Mound(1);
    Assert.assertNotNull(m.objectAt(0, 0));
  }

}
