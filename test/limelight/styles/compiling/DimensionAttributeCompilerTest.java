//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.abstrstyling.InvalidStyleAttributeError;
import limelight.styles.values.*;
import limelight.styles.values.StaticDimensionValue;
import limelight.styles.values.AutoDimensionValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DimensionAttributeCompilerTest
{
  private DimensionAttributeCompiler compiler;

  @Before
  public void setUp() throws Exception
  {
    compiler = new DimensionAttributeCompiler();
    compiler.setName("dimension");
  }

  @Test
  public void validValue() throws Exception
  {
    assertEquals(StaticDimensionValue.class, compiler.compile("123").getClass());
    assertEquals(StaticDimensionValue.class, compiler.compile("123.567").getClass());
    assertEquals(PercentageDimensionValue.class, compiler.compile("50%").getClass());
    assertEquals(PercentageDimensionValue.class, compiler.compile("3.14%").getClass());
    assertEquals(AutoDimensionValue.class, compiler.compile("auto").getClass());
    assertEquals(GreedyDimensionValue.class, compiler.compile("greedy").getClass());

    assertEquals(123, ((StaticDimensionValue)compiler.compile("123")).getPixels());
    assertEquals(0, ((StaticDimensionValue)compiler.compile("0")).getPixels());
    assertEquals(50.0, ((PercentageDimensionValue)compiler.compile("50%")).getPercentage(), 0.01);
    assertEquals(3.14, ((PercentageDimensionValue)compiler.compile("3.14%")).getPercentage(), 0.01);
  }

  @Test
  public void clojureKeywordValues() throws Exception
  {
    assertEquals(StaticDimensionValue.class, compiler.compile(":123").getClass());
  }

  @Test
  public void invalidValues() throws Exception
  {
    checkForError("-1");
    checkForError("200%");
    checkForError("blah");
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
      assertEquals("Invalid value '" + value + "' for dimension style attribute.", e.getMessage());
    }
  }
}
