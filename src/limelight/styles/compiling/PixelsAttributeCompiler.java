//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.abstrstyling.PixelsValue;
import limelight.styles.abstrstyling.StyleCompiler;
import limelight.styles.abstrstyling.StyleValue;
import limelight.styles.values.PercentagePixelsValue;
import limelight.styles.values.StaticPixelsValue;

public class PixelsAttributeCompiler extends StyleCompiler
{
  public StyleValue compile(Object objValue)
  {
    String value = stringify(objValue);
    try
    {
      PixelsValue attribute = attemptPercentageAttribute(value);
      if(attribute == null)
        attribute = attemptStaticAttribute(value);

      if(attribute != null)
        return attribute;
      else
        throw makeError(value);
    }
    catch(Exception e)
    {
      throw makeError(value);
    }
  }

  private PixelsValue attemptStaticAttribute(String value)
  {
    int intValue = IntegerAttributeCompiler.convertToInt(value);

    if(intValue >= 0)
      return new StaticPixelsValue(intValue);
    else
      return null;
  }

  private PixelsValue attemptPercentageAttribute(String value)
  {
    if(value.indexOf('%') != -1)
    {
      double percentValue = PercentageAttributeCompiler.convertToDouble(value);
      if(percentValue >= 0)
        return new PercentagePixelsValue(percentValue);
    }
    return null;
  }
}
