//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles.attributes;

import limelight.styles.StyleAttribute;
import limelight.styles.abstrstyling.StyleValue;
import limelight.ui.model.ChangeablePanel;

public class RightMarginAttribute extends StyleAttribute
{
  public RightMarginAttribute()
  {
    super("Right Margin", "pixels", "0");
  }

  @Override
  public void applyChange(ChangeablePanel panel, StyleValue value)
  {
    handleInsetChange(panel);
  }
}
