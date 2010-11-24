//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.events.panel;

import limelight.ui.Panel;

public class KeyReleasedEvent extends KeyEvent
{
  public KeyReleasedEvent(int modifiers, int keyCode, int keyLocation)
  {
    super(modifiers, keyCode, keyLocation);
  }
}
