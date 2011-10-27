//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs;

import limelight.model.api.FakePropProxy;
import limelight.ui.events.panel.ButtonPushedEvent;
import limelight.ui.events.panel.MouseClickedEvent;
import limelight.ui.events.panel.ValueChangedEvent;
import limelight.ui.model.FakeScene;
import limelight.ui.model.PropPanel;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CheckBoxPanelTest
{
  private CheckBoxPanel panel;
  private PropPanel parent;
  private FakeScene root;

  @Before
  public void setUp() throws Exception
  {
    panel = new CheckBoxPanel();
    parent = new PropPanel(new FakePropProxy());
    parent.add(panel);
    root = new FakeScene();
    root.add(parent);
  }

  @Test
  public void isButton() throws Exception
  {
    assertEquals(true, AbstractButtonPanel.class.isInstance(panel));
  }

  @Test
  public void canBeBuffered() throws Exception
  {
    assertEquals(false, panel.canBeBuffered());
  }

  @Test
  public void settingParentSetsTextAccessor() throws Exception
  {
    parent.setText("on");
    assertEquals("on", panel.getText());
  }

  @Test
  public void settingParentSterilizesParent() throws Exception
  {
    assertEquals(true, parent.isSterilized());
  }

  @Test
  public void shouldDefaultStyles() throws Exception
  {
    assertEquals("20", panel.getStyle().getWidth());
    assertEquals("20", panel.getStyle().getHeight());
  }

  @Test
  public void pushingSelectsCheckBox() throws Exception
  {
    assertEquals(false, panel.isSelected());

    new MouseClickedEvent(0, null, 0).dispatch(panel);

    assertEquals(true, panel.isSelected());
  }

  @Test
  public void consumedPushesDoNothing() throws Exception
  {
    assertEquals(false, panel.isSelected());

    new ButtonPushedEvent().consumed().dispatch(panel);

    assertEquals(false, panel.isSelected());
  }

  @Test
  public void valueChangedEventGetsThrownWhenChangingTheValue() throws Exception
  {
    final MockEventAction action = new MockEventAction();
    panel.getEventHandler().add(ValueChangedEvent.class, action);

    assertEquals(false, panel.isSelected());
    panel.setSelected(false);
    assertEquals(false, action.invoked);

    panel.setSelected(true);
    assertEquals(true, action.invoked);
  }
}
