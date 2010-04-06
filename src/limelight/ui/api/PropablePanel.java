//- Copyright � 2008-2009 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.api;

import limelight.styles.ScreenableStyle;
import limelight.ui.painting.PaintAction;
import limelight.util.Box;

import java.awt.*;

public interface PropablePanel extends limelight.ui.Panel
{
  void add(limelight.ui.Panel child);
  boolean remove(limelight.ui.Panel child);
  void removeAll();
  void doLayout();
  void repaint();
  void paintImmediately(int x, int y, int width, int height);
  void setAfterPaintAction(PaintAction action);
  void setText(String text);
  String getText();

  Box getBoundingBox();
  Box getBoxInsideBorders();
  Graphics2D getGraphics();
  ScreenableStyle getStyle();
  Prop getProp();
}
