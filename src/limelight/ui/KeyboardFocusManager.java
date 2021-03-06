//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui;

import limelight.Context;
import limelight.ui.model.StageFrame;

import java.awt.*;

public class KeyboardFocusManager extends DefaultKeyboardFocusManager
{
  public void install()
  {
    java.awt.KeyboardFocusManager.setCurrentKeyboardFocusManager(this);
    Context.instance().keyboardFocusManager = this;
  }

  public static KeyboardFocusManager installed()
  {
    KeyboardFocusManager manager = new KeyboardFocusManager();
    manager.install();
    return manager;
  }

  @Override
  public void focusNextComponent(Component aComponent)
  {
    final Window window = getActiveWindow();
    if(window instanceof StageFrame)
    {
      StageFrame frame = (StageFrame)window;
      frame.getStage().getKeyListener().focusOnNextInput();
    }
  }

  @Override
  public void focusPreviousComponent(Component aComponent)
  {
    final Window window = getActiveWindow();
    if(window instanceof StageFrame)
    {
      StageFrame frame = (StageFrame)window;
      frame.getStage().getKeyListener().focusOnPreviousInput();
    }
  }

  //
//  public void focusPanel(Panel inputPanel)
//  {
//    if(focusedPanel != inputPanel && inputPanel != null)
//    {
////      if(focusedPanel != null)
////        focusedPanel.focusLost(null);
////      focusedPanel = inputPanel;
////      focusedPanel.focusGained(null);
//    }
//  }
//
//  //TODO Hacked in
//  public void focusFrame(Frame frame)
//  {
//    this.frame = frame;
//    focusComponent(this.frame);
////    focusedPanel = frame.getRoot();
//  }
//
//  public Frame getFocusedFrame()
//  {
//    return frame;
//  }
//
//  private void focusComponent(Component newlyFocused)
//  {
//    try
//    {
//      if(newlyFocused != getGlobalFocusOwner())
//      {
//        unfocusCurrentlyFocusedComponent();
//        FocusEvent gained = new FocusEvent(newlyFocused, FocusEvent.FOCUS_GAINED);
//        FocusListener[] listeners = newlyFocused.getFocusListeners();
//        for(FocusListener listener : listeners)
//          listener.focusGained(gained);
//
//        this.setGlobalFocusOwner(newlyFocused);
//      }
//    }
//    catch(SecurityException e)
//    {
//      // happens in tests
//    }
//  }
//
//  public void unfocusCurrentlyFocusedComponent()
//  {
//    Component focused = getGlobalFocusOwner();
//    if(focused != null)
//    {
//      setGlobalFocusOwner(frame);
//      if(focusedPanel != null)
//      {
////        focusedPanel.focusLost(null);
//        focusedPanel = null;
//      }
//      FocusEvent gained = new FocusEvent(focused, FocusEvent.FOCUS_LOST);
//      FocusListener[] listeners = focused.getFocusListeners();
//      for(FocusListener listener : listeners)
//        listener.focusLost(gained);
//    }
//  }
//
//  public void focusNextComponent(Component aComponent)
//  {
//    if(focusedPanel != null)
//      focusPanel(InputPanelUtil.nextInputPanel(focusedPanel));
//  }
//
//  public void focusPreviousComponent(Component aComponent)
//  {
//    if(focusedPanel != null)
//      focusPanel(InputPanelUtil.previousInputPanel(focusedPanel));
//  }

//  public Panel getFocusedPanel()
//  {
//    return focusedPanel;
//  }
//
//  public Component getFocuedComponent()
//  {
//    return getGlobalFocusOwner();
//  }
//
//  public void releaseFrame(Frame frame)
//  {
//    if(this.frame == frame)
//    {
//      this.frame = null;
//      focusedPanel = null;
//      setGlobalFocusedWindow(null);
//      setGlobalFocusOwner(null);
//    }
//  }
}
