//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.painting;

import limelight.ui.MockGraphics;
import limelight.ui.Painter;
import limelight.ui.model.MockProp;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultPainterTest extends Assert
{
  private static Painter realBorderPainter;
  private static Painter realBackgroundPainter;

  @BeforeClass
  public static void recordPainters()
  {
    realBorderPainter = BorderPainter.instance;
    realBackgroundPainter = BackgroundPainter.instance;
  }

  @AfterClass
  public static void restorePainters()
  {
    BorderPainter.instance = realBorderPainter;
    BackgroundPainter.instance = realBackgroundPainter;
  }

  @Test
  public void shouldPaint() throws Exception
  {
    MockPainter mockBackgroundPainter = new MockPainter();
    BackgroundPainter.instance = mockBackgroundPainter;
    MockPainter mockBorderPainter = new MockPainter();
    BorderPainter.instance = mockBorderPainter;
    MockProp panel = new MockProp();
    MockGraphics graphics = new MockGraphics();

    DefaultPainter.instance.paint(graphics, panel);

    assertEquals(true, mockBackgroundPainter.painted);
    assertEquals(true, mockBorderPainter.painted);
  }
}
