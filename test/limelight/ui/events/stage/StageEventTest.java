//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.events.stage;

import limelight.Context;
import limelight.model.api.MockStageProxy;
import limelight.ui.model.FramedStage;
import limelight.ui.model.MockFrameManager;
import limelight.ui.model.inputs.MockEventAction;
import limelight.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

public class StageEventTest
{
  private FramedStage stage;

  private static class TestableStageEvent extends StageEvent
  {
  }

  @Before
  public void setUp() throws Exception
  {
    assumeTrue(TestUtil.notHeadless());
    Context.instance().frameManager = new MockFrameManager();
    stage = new FramedStage("default", new MockStageProxy());
  }

  @Test
  public void hasStageFrame() throws Exception
  {
    StageEvent event = new TestableStageEvent();
    assertEquals(null, event.getStage());

    event.setStage(stage);

    assertEquals(stage, event.getStage());
  }

  @Test
  public void dispatching() throws Exception
  {
    StageEvent event = new TestableStageEvent();
    MockEventAction action = new MockEventAction();
    stage.getEventHandler().add(TestableStageEvent.class, action);

    event.dispatch(stage);

    assertEquals(true, action.invoked);
  }
}
