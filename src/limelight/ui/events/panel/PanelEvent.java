package limelight.ui.events.panel;

import limelight.events.Event;
import limelight.ui.Panel;

public abstract class PanelEvent extends Event
{
  private Panel source;
  private Panel recipient;

  @Override
  public String toString()
  {
    return super.toString() + ": source=" + getSource() + " recipient=" + getRecipient();
  }

  public void setSource(Panel source)
  {
    subject = source;
    this.source = source;
    recipient = source;
  }

  public Panel getSource()
  {
    return source;
  }

  public PanelEvent consumed()
  {
    consume();
    return this;
  }

  public Panel getRecipient()
  {
    return recipient;
  }

  public void setRecipient(Panel panel)
  {
    recipient = panel;
  }

  public boolean isInheritable()
  {
    return false;
  }

  public void dispatch(Panel panel)
  {
    if(source == null)
      setSource(panel);
    
    Panel previousRecipient = recipient;
    setRecipient(panel);
    final PanelEventHandler eventHandler = recipient.getEventHandler();
    eventHandler.dispatch(this);
    setRecipient(previousRecipient);
  }
}
