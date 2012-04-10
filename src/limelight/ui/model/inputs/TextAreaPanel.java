//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs;

import limelight.styles.Style;
import limelight.ui.model.text.MultiLineTextModel;
import limelight.ui.model.text.TextModel;

public class TextAreaPanel extends TextInputPanel
{

  @Override
  protected TextModel createModel()
  {
    return new MultiLineTextModel(this);
  }

  @Override
  protected void setDefaultStyles(Style style)
  {
    style.setDefault(Style.WIDTH, 150);
    style.setDefault(Style.HEIGHT, 75);
    style.setDefault(Style.CURSOR, "text");
    style.setDefault(Style.BACKGROUND_COLOR, "white");
    setBorderStyleDefaults(style);
    setPaddingDefaults(style);
  }
}
