package limelight.caching;

import java.lang.ref.SoftReference;

public abstract class CacheEntry<T>
{
  private SoftReference<T> entry;

  public CacheEntry(T value)
  {
    entry = new SoftReference<T>(value);
  }

  public CacheEntry()
  {
  }

  public T value()
  {
    return entry.get();
  }

  public abstract boolean isExpired();
  public abstract void renew();
}
