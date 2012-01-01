//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.values;

import junit.framework.TestCase;
import limelight.styles.abstrstyling.DimensionValue;
import limelight.styles.abstrstyling.NoneableValue;

public class AutoDimensionValueTest extends TestCase
{
  private AutoDimensionValue auto;

  public void setUp() throws Exception
  {
    auto = new AutoDimensionValue();
  }

  public void testToString() throws Exception
  {
    assertEquals("auto", auto.toString());
  }

  public void testEquals() throws Exception
  {
    assertEquals(true, auto.equals(auto));
    assertEquals(true, auto.equals(new AutoDimensionValue()));
    assertEquals(false, auto.equals(null));
  }

  public void testCalculateDimensionWithNoMinOrMax() throws Exception
  {
    NoneableValue<DimensionValue> min = new NoneableValue<DimensionValue>(null);
    NoneableValue<DimensionValue> max = new NoneableValue<DimensionValue>(null);

    assertEquals(100, auto.calculateDimension(100, min, max, 0));
  }

  public void testCalculateDimensionWithMax() throws Exception
  {
    NoneableValue<DimensionValue> min = new NoneableValue<DimensionValue>(null);
    NoneableValue<DimensionValue> max = new NoneableValue<DimensionValue>(new StaticDimensionValue(50));

    assertEquals(50, auto.calculateDimension(100, min, max, 0));
  }

  public void testCollapseExcessWithNoMinOrMax() throws Exception
  {
    NoneableValue<DimensionValue> min = new NoneableValue<DimensionValue>(null);
    NoneableValue<DimensionValue> max = new NoneableValue<DimensionValue>(null);

    assertEquals(100, auto.collapseExcess(200, 100, min, max));
  }

  public void testCollapseExcessWithMin() throws Exception
  {
    NoneableValue<DimensionValue> min = new NoneableValue<DimensionValue>(new StaticDimensionValue(150));
    NoneableValue<DimensionValue> max = new NoneableValue<DimensionValue>(null);

    assertEquals(150, auto.collapseExcess(200, 100, min, max));
  }

  public void testCollapseExcessWithMax() throws Exception
  {
    NoneableValue<DimensionValue> min = new NoneableValue<DimensionValue>(null);
    NoneableValue<DimensionValue> max = new NoneableValue<DimensionValue>(new StaticDimensionValue(50));

    assertEquals(50, auto.collapseExcess(200, 100, min, max));
  }
}
