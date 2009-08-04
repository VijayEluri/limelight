//- Copyright � 2008-2009 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles.styling;

import junit.framework.TestCase;
import limelight.styles.abstrstyling.StyleAttribute;
import limelight.styles.VerticalAlignment;
import limelight.util.Box;

public class SimpleVerticalAlignmentAttributeTest extends TestCase
{
  private SimpleVerticalAlignmentAttribute top;
  private SimpleVerticalAlignmentAttribute center;
  private SimpleVerticalAlignmentAttribute bottom;

  public void setUp() throws Exception
  {
    top = new SimpleVerticalAlignmentAttribute(VerticalAlignment.TOP);
    center = new SimpleVerticalAlignmentAttribute(VerticalAlignment.CENTER);
    bottom = new SimpleVerticalAlignmentAttribute(VerticalAlignment.BOTTOM);
  }

  public void testCreation() throws Exception
  {
    assertEquals(true, (top instanceof StyleAttribute));
    assertEquals(VerticalAlignment.TOP, top.getAlignment());
  }

  public void testToString() throws Exception
  {
    assertEquals("top", top.toString());
    assertEquals("center", center.toString());
    assertEquals("bottom", bottom.toString());
  }

  public void testEquality() throws Exception
  {
    assertEquals(true, top.equals(top));
    assertEquals(true, top.equals(new SimpleVerticalAlignmentAttribute(VerticalAlignment.TOP)));
    assertEquals(false, top.equals(bottom));
    assertEquals(false, top.equals(null));
  }
  
  public void testStartingY() throws Exception
  {
    Box area = new Box(0, 0, 100, 100);
    assertEquals(0, top.getY(100, area));
    assertEquals(0, top.getY(1, area));

    assertEquals(0, center.getY(100, area));
    assertEquals(25, center.getY(50, area));
    assertEquals(37, center.getY(25, area));
    assertEquals(49, center.getY(1, area));

    assertEquals(0, bottom.getY(100, area));
    assertEquals(50, bottom.getY(50, area));
    assertEquals(99, bottom.getY(1, area));
  }
}
