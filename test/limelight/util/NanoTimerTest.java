//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NanoTimerTest extends Assert
{
  private final int ONE_MILLION = 1000000;
  private final int TEN_MILLION = 10 * ONE_MILLION;
  private final int NINE_MILLION = 9 * ONE_MILLION;
  private final int ELEVEN_MILLION = 11 * ONE_MILLION;
  private final int TWENTY_MILLION = 20 * ONE_MILLION;

  private NanoTimer timer;

  @Before
  public void setUp() throws Exception
  {
    timer = new NanoTimer();
  }

  @Test
  public void startTime() throws Exception
  {
    long now = System.nanoTime();
    long then = timer.getTimeOfLastActivity();
    assertEquals("" + (now - then), true, now - then < ONE_MILLION); // less than 1 milisecond
  }

  @Test
  public void idleTime() throws Exception
  {
    assertEquals(true, timer.getIdleNanos() < ONE_MILLION);
    Thread.sleep(10); // Doesn't necessarily sleep a full 10 millis
    assertEquals("" + timer.getIdleNanos() + " should > NINE_MILLION", true, timer.getIdleNanos() > NINE_MILLION);
  }

//  @Test
//  public void sleeping()
//  {
//    System.gc();
//    long before = System.nanoTime();
//    timer.sleep(TEN_MILLION);
//    long after = System.nanoTime();
//    long sleepDuration = after - before;
//
//    assertEquals("actual sleep duration: " + sleepDuration, true, (sleepDuration > NINE_MILLION && sleepDuration < TWENTY_MILLION));
//    assertEquals(true, timer.getActualSleepDuration() > NINE_MILLION && timer.getActualSleepDuration() < TWENTY_MILLION);
//    assertEquals(true, timer.getIdleNanos() < ONE_MILLION);
//    assertEquals(true, timer.getSleepJiggle() < ONE_MILLION);
//  }

  @Test
  public void sleepDurationAndJiggle() throws Exception
  {
    for(int i = 0; i < 10; i++)
    {
      long before = System.nanoTime();
      timer.sleep(TEN_MILLION);
      long after = System.nanoTime();
      long sleepDuration = after - before;

//      System.err.println("sleepDuration = " + sleepDuration + " actual: " + timer.getActualSleepDuration() + " jiggle: " + timer.getSleepJiggle());

      if(sleepDuration > TEN_MILLION)
      {
        assertEquals(true, timer.getSleepJiggle() < 0);
      }
      else if(sleepDuration < TEN_MILLION)
      {
        assertEquals(true, timer.getSleepJiggle() > 0);
      }
      else
      {
        assertEquals(0, timer.getSleepJiggle());
      }
    }
  }

  @Test
  public void doesntSleepIfDurationIsZeroOrLess() throws Exception
  {
    Thread.sleep(10);

    timer.sleep(-1234567890);
    long mark = timer.getIdleNanos();
    assertEquals(0, timer.getActualSleepDuration());
    assertEquals("time of last activity: " + mark, true, mark < ONE_MILLION);
    assertEquals(0, timer.getSleepJiggle());

    Thread.sleep(10);
    timer.sleep(TEN_MILLION);

    timer.sleep(0);
    mark = timer.getIdleNanos();
    assertEquals(0, timer.getActualSleepDuration());
    assertEquals("time of last activity: " + mark, true, mark < ONE_MILLION);
    assertEquals(0, timer.getSleepJiggle());
  }

  @Test
  public void moveMarkBack() throws Exception
  {
    timer.markTime();
    long mark = timer.getTimeOfLastActivity();

    timer.moveMarkBackInTime(12345);

    assertEquals(12345, (mark - timer.getTimeOfLastActivity()));
  }
}
