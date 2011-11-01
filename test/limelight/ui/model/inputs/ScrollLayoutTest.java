//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs;

import junit.framework.TestCase;
import limelight.model.api.FakePropProxy;
import limelight.ui.model.*;

public class ScrollLayoutTest extends TestCase
{
  private PropPanel parent;

  public void setUp() throws Exception
  {
    ScenePanel root = new ScenePanel(new FakePropProxy());
    root.setStage(new MockStage());
    parent = new PropPanel(new FakePropProxy());
    root.add(parent);
    parent.getStyle().setWidth("100");
    parent.getStyle().setHeight("100");
  }

  public void testScrollContentThatIsntAlignedTopLeft() throws Exception
  {
    parent.getStyle().setScrollbars("on");
    parent.getStyle().setAlignment("center");
    PropPanel panel = addChildWithSize(parent, "200", "200");
    PropPanelLayout.instance.doLayout(parent);

    parent.getVerticalScrollbar().setValue(1);
    ScrollLayout verticalLayout = new ScrollLayout(parent.getVerticalScrollbar());
    verticalLayout.doLayout(parent);
    assertEquals(-1, panel.getY());

    parent.getHorizontalScrollbar().setValue(2);
    ScrollLayout horizontalLayout = new ScrollLayout(parent.getHorizontalScrollbar());
    horizontalLayout.doLayout(parent);
    assertEquals(-2, panel.getX());
  }

  private PropPanel addChildWithSize(ParentPanelBase parent, String width, String height)
  {
    PropPanel panel = new PropPanel(new FakePropProxy());
    panel.getStyle().setWidth(width);
    panel.getStyle().setHeight(height);
    parent.add(panel);
    return panel;
  }
}
