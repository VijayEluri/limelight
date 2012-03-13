//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs;

import limelight.ui.MockTypedLayoutFactory;
import limelight.ui.model.text.offsetting.XOffsetStrategy;
import limelight.ui.model.text.offsetting.YOffsetStrategy;
import limelight.ui.model.text.TextContainer;
import limelight.ui.model.text.TextModel;
import limelight.ui.text.TextLocation;
import limelight.ui.text.TypedLayout;
import limelight.util.Box;

import java.awt.*;
import java.util.ArrayList;

public class MockTextModel extends TextModel
{
  public boolean clearLayoutsCalled;

  public MockTextModel(TextContainer container)
  {
    super(container);
    setTypedLayoutFactory(MockTypedLayoutFactory.instance);
  }

  @Override
  public void clearCache()
  {
    super.clearCache();
    clearLayoutsCalled = true;
  }

  @Override
  protected void buildLines(ArrayList<TypedLayout> lines)
  {
    lines.add(createLayout(getDisplayableText()));
  }

  @Override
  protected XOffsetStrategy getDefaultXOffsetStrategy()
  {
    return XOffsetStrategy.CENTERED;
  }

  @Override
  protected YOffsetStrategy getDefaultYOffsetStrategy()
  {
    return YOffsetStrategy.FITTING;
  }

  @Override
  public Dimension getTextDimensions()
  {
    TypedLayout activeLayout = getLines().get(0);
    return new Dimension(activeLayout.getWidth(), activeLayout.getHeight());
  }

  @Override
  public TextLocation getLocationAt(Point point)
  {
    return TextLocation.at(0, 0);
  }

  @Override
  public Box getCaretShape()
  {
    return getLines().get(0).getCaretShape(getCaretLocation().index);
  }

  @Override
  public ArrayList<Box> getSelectionRegions()
  {
    return null;
  }

  public void addLayout(String value)
  {
    getLines().add(createLayout(value));
  }
}
