//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.abstrstyling.InvalidStyleAttributeError;
import limelight.styles.HorizontalAlignment;
import limelight.styles.values.StaticXCoordinateValue;
import limelight.styles.values.PercentageXCoordinateValue;
import limelight.styles.values.AlignedXCoordinateValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XCoordinateAttributeCompilerTest
{
  private XCoordinateAttributeCompiler compiler;

  @Before
  public void setUp() throws Exception
  {
    compiler = new XCoordinateAttributeCompiler();
    compiler.setName("x-coordinate");
  }

  @Test
  public void validValue() throws Exception
  {
    assertEquals(StaticXCoordinateValue.class, compiler.compile("123").getClass());
    assertEquals(StaticXCoordinateValue.class, compiler.compile("-123").getClass());
    assertEquals(StaticXCoordinateValue.class, compiler.compile("123.567").getClass());
    assertEquals(PercentageXCoordinateValue.class, compiler.compile("50%").getClass());
    assertEquals(PercentageXCoordinateValue.class, compiler.compile("3.14%").getClass());
    assertEquals(AlignedXCoordinateValue.class, compiler.compile("left").getClass());
    assertEquals(AlignedXCoordinateValue.class, compiler.compile("center").getClass());
    assertEquals(AlignedXCoordinateValue.class, compiler.compile("right").getClass());

    assertEquals(123, ((StaticXCoordinateValue) compiler.compile("123")).getValue());
    assertEquals(-123, ((StaticXCoordinateValue) compiler.compile("-123")).getValue());
    assertEquals(0, ((StaticXCoordinateValue) compiler.compile("0")).getValue());
    assertEquals(50.0, ((PercentageXCoordinateValue) compiler.compile("50%")).getPercentage(), 0.01);
    assertEquals(3.14, ((PercentageXCoordinateValue) compiler.compile("3.14%")).getPercentage(), 0.01);
    assertEquals(HorizontalAlignment.LEFT, ((AlignedXCoordinateValue)compiler.compile("left")).getAlignment());
    assertEquals(HorizontalAlignment.CENTER, ((AlignedXCoordinateValue)compiler.compile("center")).getAlignment());
    assertEquals(HorizontalAlignment.RIGHT, ((AlignedXCoordinateValue)compiler.compile("right")).getAlignment());
  }

  @Test
  public void validValueWithColon() throws Exception
  {
    assertEquals(123, ((StaticXCoordinateValue) compiler.compile(":123")).getValue());
    assertEquals(-123, ((StaticXCoordinateValue) compiler.compile(":-123")).getValue());
    assertEquals(0, ((StaticXCoordinateValue) compiler.compile(":0")).getValue());
    assertEquals(50.0, ((PercentageXCoordinateValue) compiler.compile(":50%")).getPercentage(), 0.01);
    assertEquals(HorizontalAlignment.LEFT, ((AlignedXCoordinateValue)compiler.compile(":left")).getAlignment());
    assertEquals(HorizontalAlignment.CENTER, ((AlignedXCoordinateValue)compiler.compile(":center")).getAlignment());
    assertEquals(HorizontalAlignment.RIGHT, ((AlignedXCoordinateValue)compiler.compile(":right")).getAlignment());
  }

  @Test
  public void invalidValues() throws Exception
  {
    checkForError("200%");
    checkForError("blah");
    checkForError("top");
  }

  private void checkForError(String value)
  {
    try
    {
      compiler.compile(value);
      fail("should have throw error");
    }
    catch(InvalidStyleAttributeError e)
    {
      assertEquals("Invalid value '" + value + "' for x-coordinate style attribute.", e.getMessage());
    }
  }
}
