package limelight.ui.model2;

import limelight.util.Box;
import limelight.ui.Panel;
import limelight.styles.Style;
import java.util.LinkedList;
import java.util.HashSet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class RootPanel implements Panel
{
  private Panel panel;
  private Container contentPane;
  private EventListener listener;
  private boolean alive;
  private HashSet<Panel> changedPanels;
  private Frame frame;

  public RootPanel(Frame frame)
  {
    this.frame = frame;
    contentPane = frame.getContentPane();
    changedPanels = new HashSet<Panel>();
  }

  public Container getContentPane()
  {
    return contentPane;
  }

  public Box getChildConsumableArea()
  {
    return new Box(getX(), getY(), contentPane.getWidth(), contentPane.getHeight());
  }

  public Box getBoxInsidePadding()
  {
    return getChildConsumableArea();
  }

  public void doLayout()
  {
    panel.doLayout();
  }

  public int getWidth()
  {
    return contentPane.getWidth();
  }

  public int getHeight()
  {
    return contentPane.getHeight();
  }

  public void setLocation(int x, int y)
  {
    contentPane.setLocation(x, y);
  }

  public Point getLocation()
  {
    return contentPane.getLocation();
  }

  public void setSize(int width, int height)
  {
    contentPane.setSize(width, height);
  }

  public Box getAbsoluteBounds()
  {
    return new Box(contentPane.getBounds());
  }

  public Point getAbsoluteLocation()
  {
    return contentPane.getLocation();
  }

  public int getX()
  {
    return getAbsoluteLocation().x;
  }

  public int getY()
  {
    return getAbsoluteLocation().y;
  }

  public void setPanel(Panel child)
  { 
    panel = child;
    panel.setParent(this);

    listener = new EventListener(panel);
    contentPane.addMouseListener(listener);
    contentPane.addMouseMotionListener(listener);
    contentPane.addMouseWheelListener(listener);
    contentPane.addKeyListener(listener);
    alive = true;
  }

  public void destroy()
  {
    contentPane.removeMouseListener(listener);
    contentPane.removeMouseMotionListener(listener);
    contentPane.removeMouseWheelListener(listener);
    contentPane.removeKeyListener(listener);
    listener = null;
    panel.setParent(null);
    changedPanels.clear();
    alive = false;
  }

  public Panel getRoot()
  {
    return this;
  }

  public Graphics2D getGraphics()
  {
    return (Graphics2D)contentPane.getGraphics();
  }

  public boolean isAncestor(Panel panel)
  {
    return panel == this;
  }

  public Panel getClosestCommonAncestor(Panel panel)
  {
    return this;
  }

  public void repaint()
  {
    doLayout();
    PaintJob job = new PaintJob(getAbsoluteBounds());
    job.paint(panel);
    job.applyTo(getGraphics());
  }


  public void setCursor(Cursor cursor)
  {
    contentPane.setCursor(cursor);
  }

  public Panel getPanel()
  {
    return panel;
  }

  public boolean isAlive()
  {
    return alive;
  }

  public EventListener getListener()
  {
    return listener;
  }

  public synchronized void addChangedPanel(Panel panel)
  {  
    changedPanels.add(panel);
  }

  public synchronized int getChangedPanelCount()
  {
    return changedPanels.size();
  }

  //TODO MDM - This is not fully right.  Need to make sure we're not repainting a panel more than once.  So we need to remove from the set descendants of other panels in the list.
  //TODO MDM - Also, we should not have to figure out if a parent needs the updating here.  That's not right.
  public synchronized void repaintChangedPanels()
  {
    for(Panel changedPanel : changedPanels)
    {
      Style style = changedPanel.getStyle();
      boolean dimentionsChanged = style.changed(Style.WIDTH) || style.changed(Style.HEIGHT);
      boolean locationChanged = "on".equals(style.getFloat()) && (style.changed(Style.X) || style.changed(Style.Y));
      Update update = changedPanel.getNeededUpdate();
      if(dimentionsChanged || locationChanged)
        update.performUpdate(changedPanel.getParent());
      else
        update.performUpdate(changedPanel);
      changedPanel.resetNeededUpdate();
    }
    changedPanels.clear();
  }

  public synchronized boolean changedPanelsContains(Panel panel)
  {
    return changedPanels.contains(panel);
  }

  /////////////////////////////////////////////
  /// NOT NEEDED
  /////////////////////////////////////////////

  public void paintOn(Graphics2D graphics)
  {
  }

  public boolean canBeBuffered()
  {
    return false;
  }

  public boolean hasChildren()
  {
    return true;
  }

  public LinkedList<Panel> getChildren()
  {
    LinkedList<Panel> panels = new LinkedList<Panel>();
    panels.add(panel);
    return panels;
  }

  public void replace(Panel child, Panel newChild)
  {
  }

  public boolean remove(Panel child)
  {
    return false;
  }

  public void removeAll()
  {
  }

  public boolean containsAbsolutePoint(Point point)
  {
    return false;
  }

  public void setParent(Panel panel)
  {
  }

  public void sterilize()
  {
  }

  public boolean isSterilized()
  {
    return false;
  }

  public Panel getParent()
  {
    return null;
  }

  public Style getStyle()
  {
    throw new RuntimeException("RootPanel.getStyle()");
  }

  public boolean isFloater()
  {
    return false;
  }

  public boolean containsRelativePoint(Point point)
  {
    return true;
  }

  public Panel getOwnerOfPoint(Point point)
  {
    point = new Point(point.x - getX(), point.y - getY());
    if(panel.containsRelativePoint(point))
      return panel.getOwnerOfPoint(point);
    return this;
  }

  public void clearCache()
  {
  }

  public void mousePressed(MouseEvent e)
  {
  }

  public void mouseReleased(MouseEvent e)
  {
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mouseDragged(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

  public void mouseMoved(MouseEvent e)
  {
  }

  public void mouseWheelMoved(MouseWheelEvent e)
  {
  }

  public void resetNeededUpdate()
  {
  }

  public Update getNeededUpdate()
  {
    return null;
  }

  public void add(Panel child)
  {
  }

  public Frame getFrame()
  {
    return frame;
  }
}
