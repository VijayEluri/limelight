//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.attributes;

import limelight.Context;
import limelight.caching.SimpleCache;
import limelight.styles.StyleAttribute;
import limelight.ui.Panel;
import limelight.ui.model.FloaterLayout;
import limelight.ui.model.MockChangeablePanel;
import limelight.ui.model.MockTextAccessor;
import org.junit.Assert;

import java.awt.image.BufferedImage;

public class AbstractStyleAttributeTestBase extends Assert
{
  protected StyleAttribute attribute;
  protected MockChangeablePanel panel;
  protected SimpleCache<Panel, BufferedImage> cache;

  public void setUpPanel() throws Exception
  {
    panel = new MockChangeablePanel();
    cache = new SimpleCache<Panel, BufferedImage>();
    Context.instance().bufferedImageCache = cache;
    cache.cache(panel, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
  }

  protected void checkBorderChange() throws Exception
  {
    setUpPanel();

    attribute.applyChange(panel, null);

    assertEquals(true, panel.propagateSizeChangeDownCalled);
    assertEquals(true, panel.borderChanged);
    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(true, panel.clearCacheCalled);
    assertEquals(null, cache.retrieve(panel));
  }

  protected void checkInsetChange() throws Exception
  {
    setUpPanel();

    attribute.applyChange(panel, null);

    assertEquals(true, panel.propagateSizeChangeDownCalled);
    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(true, panel.clearCacheCalled);
    assertEquals(null, cache.retrieve(panel));
  }

  protected void checkDimensionChange() throws Exception
  {
    setUpPanel();

    attribute.applyChange(panel, null);

    assertEquals(true, panel.sizeChangePending);
    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(true, panel.propagateSizeChangeUpCalled);
    assertEquals(true, panel.propagateSizeChangeDownCalled);
    assertEquals(null, cache.retrieve(panel));
  }

  protected void checkCoordinateChange() throws Exception
  {
    setUpPanel();

    attribute.applyChange(panel, null);

    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(FloaterLayout.instance, panel.neededLayout);
    assertNotNull(cache.retrieve(panel));
  }

  protected void checkDefaultLayoutRequested() throws Exception
  {
    setUpPanel();

    attribute.applyChange(panel, null);

    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(null, panel.neededLayout);
    assertEquals(null, cache.retrieve(panel));
  }

  protected void checkFontChange() throws Exception
  {
    setUpPanel();
    MockTextAccessor accessor = new MockTextAccessor();
    panel.textAccessor = accessor;

    attribute.applyChange(panel, null);

    assertEquals(true, panel.sizeChangePending);
    assertEquals(true, accessor.markAsNeedingLayoutCalled);
    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(true, panel.propagateSizeChangeUpCalled);
    assertEquals(null, cache.retrieve(panel));
  }

  protected void checkAlignmentChange() throws Exception
  {
    setUpPanel();
    MockTextAccessor accessor = new MockTextAccessor();
    panel.textAccessor = accessor;

    attribute.applyChange(panel, null);

    assertEquals(true, accessor.markAsNeedingLayoutCalled);
    assertEquals(true, panel.markAsNeedingLayoutCalled);
    assertEquals(null, cache.retrieve(panel));
  }
}
