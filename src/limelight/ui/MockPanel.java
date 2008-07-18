package limelight.ui;

import limelight.ui.model2.BasePanel;
import limelight.util.Box;
import limelight.styles.Style;
import limelight.styles.FlatStyle;

import java.awt.*;

public class MockPanel extends BasePanel
{
  public FlatStyle style;
  public static int paintCount;
  public int paintIndex;
  public boolean wasPainted;
  public boolean canBeBuffered;
  public boolean changeMarkerWasReset;
  public boolean wasLaidOut;

  public MockPanel()
  {
    style = new FlatStyle();
    canBeBuffered = true;
  }

  public Box getChildConsumableArea()
  {
    return new Box(0, 0, getWidth(), getHeight());
  }

  public Box getBoxInsidePadding()
  {
    return getChildConsumableArea();
  }

  public Style getStyle()
  {
    return style;
  }

  public void paintOn(Graphics2D graphics)
  {
    super.paintOn(graphics);
    wasPainted = true;
    paintIndex = paintCount++;
  }

  public boolean canBeBuffered()
  {
    return canBeBuffered;
  }

  public void resetNeededUpdate()
  {   
    super.resetNeededUpdate();
    changeMarkerWasReset = true;
  }

  public void doLayout()
  {
    wasLaidOut = true;
  }
}
