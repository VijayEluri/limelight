//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles.attributes;

import limelight.styles.compiling.DimensionAttributeCompiler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CyclicGradientAttributeTest extends Assert
{
  private CyclicGradientAttribute attribute;

  @Before
  public void setUp() throws Exception
  {
    attribute = new CyclicGradientAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Cyclic Gradient", attribute.getName());
    assertEquals("on/off", attribute.getCompiler().type);
    assertEquals("off", attribute.getDefaultValue().toString());
  }
}
