//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.text.offsetting;

import limelight.ui.text.TextLocation;
import limelight.ui.text.TypedLayout;
import limelight.util.Box;

public class FittingYOffsetStrategy implements YOffsetStrategy
{
  public int calculateYOffset(limelight.ui.model.text.TextModel model)
  {
    int yOffset = model.getYOffset();
    Box boundingBox = model.getContainer().getConsumableBounds();
    TextLocation caretLocation = model.getCaretLocation();
    int absoluteCaretY = model.getAbsoluteY(caretLocation);
    int relativeCaretY = absoluteCaretY + yOffset;
    TypedLayout caretLine = model.getLines().get(caretLocation.line);
    int caretHeight = caretLine.getHeight();

    if(caretHeight > boundingBox.height)
      yOffset = (caretHeight - boundingBox.height) / -2;
    else if(relativeCaretY + caretHeight >= boundingBox.height)
      yOffset = -absoluteCaretY - caretHeight + boundingBox.height;
    else if(absoluteCaretY < 0)
      yOffset = 0;
    else if(relativeCaretY < 0)
      yOffset = -absoluteCaretY;

    return yOffset;
  }
}
