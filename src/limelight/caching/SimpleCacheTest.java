package limelight.caching;

import junit.framework.TestCase;

public class SimpleCacheTest extends TestCase
{
  public void testCreateSimpleCacheEntries() throws Exception
  {
    SimpleCache<String, String> cache = new SimpleCache<String, String>();
    cache.cache("1", "one");

    assertEquals(SimpleCacheEntry.class, cache.getMap().get("1").getClass());
  }
}
