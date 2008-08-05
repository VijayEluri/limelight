package limelight.ui.model2.inputs;

import limelight.ui.Panel;
import limelight.ui.model2.BasePanel;
import limelight.ui.model2.updates.Updates;
import limelight.util.Box;
import limelight.styles.Style;
import limelight.Context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class InputPanel extends BasePanel
{
  private Component component;

  protected InputPanel()
  {
    component = createComponent();
  }

  protected abstract Component createComponent();

  public Component getComponent()
  {
    return component;
  }

  public Box getChildConsumableArea()
  {
    return getBoundingBox();
  }

  public Box getBoxInsidePadding()
  {
    return getBoundingBox();
  }

  public Style getStyle()
  {
    return getParent().getStyle();
  }

  public int getWidth()
  {
    return component.getWidth();
  }

  public int getHeight()
  {
    return component.getHeight();
  }

  public void paintOn(Graphics2D graphics)
  {
    component.paint(graphics);
  }

  public void mousePressed(MouseEvent e)
  {
    e.setSource(component);
    for(MouseListener mouseListener : component.getMouseListeners())
      mouseListener.mousePressed(translatedEvent(e));
  }

  public void mouseReleased(MouseEvent e)
  {
    e.setSource(component);
    for(MouseListener mouseListener : component.getMouseListeners())
      mouseListener.mouseReleased(translatedEvent(e));
  }

  public void mouseClicked(MouseEvent e)
  {
    Context.instance().keyboardFocusManager.focusPanel(this);
    for(MouseListener mouseListener : component.getMouseListeners())
      mouseListener.mouseClicked(translatedEvent(e));
    setNeededUpdate(Updates.shallowPaintUpdate);
  }

  public void mouseDragged(MouseEvent e)
  {
    e.setSource(component);
    for(MouseMotionListener mouseListener : component.getMouseMotionListeners())
      mouseListener.mouseDragged(translatedEvent(e));
  }

  public InputPanel nextInputPanel()
  {
    InputPanel next = null;
    InputPanel first = null;
    boolean foundMe = false;
    
    for(Panel panel : getRoot())
    {
      if(panel instanceof InputPanel)
      {
        if(foundMe)
        {
          next = (InputPanel)panel;
          break;
        }
        else if(panel == this)
          foundMe = true;
        if(first == null)
          first = (InputPanel)panel;
      }
    }

    if(next != null)
      return next;
    else
      return first;
  }

  public InputPanel previousInputPanel()
  {
    InputPanel previous = null;

    for(Panel panel : getRoot())
    {
      if(panel instanceof InputPanel)
      {
        if(panel == this && previous != null)
        {
          break;
        }
        else
        {
          previous = (InputPanel)panel;
        }
      }
    }

    return previous;
  }
}