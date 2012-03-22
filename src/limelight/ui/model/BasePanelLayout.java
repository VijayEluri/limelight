//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model;

import limelight.ui.Panel;

public class BasePanelLayout implements Layout
{
  public static BasePanelLayout instance = new BasePanelLayout();
  public Panel lastPanelProcessed;

  public void doLayout(Panel thePanel)
  {
    PanelBase panel = (PanelBase) thePanel;
    panel.wasLaidOut();
    lastPanelProcessed = thePanel;
  }

  public boolean overides(Layout other)
  {
    return false;
  }

  public void doLayout(Panel panel, boolean topLevel)
  {
    doLayout(panel);
  }

}
