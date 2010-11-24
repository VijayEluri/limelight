//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.model.api;

import limelight.model.Stage;

import java.util.Map;

public interface TheaterProxy
{
  Stage buildStage(String name, Map<String, Object> options);
}
