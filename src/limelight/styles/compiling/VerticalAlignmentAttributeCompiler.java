//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.VerticalAlignment;
import limelight.styles.abstrstyling.StyleCompiler;
import limelight.styles.abstrstyling.StyleValue;
import limelight.styles.values.SimpleVerticalAlignmentValue;

public class VerticalAlignmentAttributeCompiler extends StyleCompiler
{
  public StyleValue compile(Object value)
  {
    VerticalAlignment alignment = parse(value);
    if(alignment != null)
      return new SimpleVerticalAlignmentValue(alignment);
    else
      throw makeError(value);
  }

  public static VerticalAlignment parse(Object value)
  {
    String lowerCase = stringify(value).toLowerCase().trim();
    if("top".equals(lowerCase))
      return VerticalAlignment.TOP;
    else if("center".equals(lowerCase) || "middle".equals(lowerCase))
      return VerticalAlignment.CENTER;
    else if("bottom".equals(lowerCase))
      return VerticalAlignment.BOTTOM;
    else
      return null;
  }
}
