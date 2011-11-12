//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs.offsetting;

import limelight.ui.model.inputs.TextModel;
import limelight.util.Box;

public class FittingXOffsetStrategy implements XOffsetStrategy
{
  public int calculateXOffset(TextModel model)
  {
    int xOffset = model.getXOffset();
    Box boundingBox = model.getContainer().getConsumableBounds();
    int absoluteCaretX = model.getAbsoluteX(model.getCaretLocation());
    int relativeCaretX = absoluteCaretX + xOffset;

    if(relativeCaretX >= boundingBox.width)
      xOffset = (absoluteCaretX - boundingBox.width + model.getCaretWidth()) * -1;
    else if(relativeCaretX < 0)
      xOffset = absoluteCaretX * -1;

    return xOffset;
  }
}
