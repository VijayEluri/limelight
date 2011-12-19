import limelight.events.Event;

public class SamplePlayer
{
  public int invocations;
  public Event event;
  public static SamplePlayer lastInstance;

  public SamplePlayer()
  {
    lastInstance = this;
  }

  public void sampleAction()
  {
    invocations++;
  }

  public void sampleActionWithEvent(limelight.events.Event e)
  {
    invocations++;
    event = e;
  }
}
