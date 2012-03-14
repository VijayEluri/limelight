//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.abstrstyling.StringValue;
import limelight.styles.abstrstyling.StyleCompiler;
import limelight.styles.abstrstyling.StyleValue;

public class StringAttributeCompiler extends StyleCompiler
{
  public StyleValue compile(Object value)
  {
     return new StringValue(value);
  }
}
