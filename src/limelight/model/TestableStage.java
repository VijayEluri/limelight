package limelight.model;

import limelight.model.api.StageProxy;

import java.awt.*;

public class TestableStage extends Stage
{
  private boolean visible;
  private boolean opened;
  private boolean framed;
  private boolean alwaysOnTop;
  public Insets insets;
  private Cursor cursor;
  private Graphics graphics;

  public TestableStage(String name, StageProxy proxy)
  {
    super(name);
    setProxy(proxy);
  }

  @Override
  protected void doOpen()
  {
    opened = true;
  }

  @Override
  protected void doClose()
  {
    opened = false;
  }

  @Override
  public boolean isOpen()
  {
    return opened;
  }

  @Override
  public boolean isVisible()
  {
    return visible;
  }

  @Override
  public int getWidth()
  {
    return 0;
  }

  @Override
  public int getHeight()
  {
    return 0;
  }

  @Override
  public Graphics getGraphics()
  {
    return graphics;
  }

  @Override
  public Cursor getCursor()
  {
    return cursor;
  }

  @Override
  public void setCursor(Cursor cursor)
  {
    this.cursor = cursor;
  }

  @Override
  public Insets getInsets()
  {
    return insets;
  }

  @Override
  public void setFramed(boolean framed)
  {
    this.framed = framed;
  }

  @Override
  public boolean isFramed()
  {
    return framed;
  }

  @Override
  public void setAlwaysOnTop(boolean value)
  {
    alwaysOnTop = value;
  }

  @Override
  public boolean isAlwaysOnTop()
  {
    return alwaysOnTop;
  }

  @Override
  protected void setVisible(boolean value)
  {
    visible = value;
  }
}
