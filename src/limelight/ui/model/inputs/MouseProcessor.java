package limelight.ui.model.inputs;

import java.awt.event.MouseEvent;
import java.awt.font.TextHitInfo;
import java.util.Date;

public class MouseProcessor
{

  TextModel boxInfo;
  public long lastClickTime;
  public boolean doubleClickOn;

  public MouseProcessor(TextModel boxInfo)
  {
    this.boxInfo = boxInfo;
  }

  public boolean isMouseEventInBox(MouseEvent e)
  {
    int myX = e.getX() - boxInfo.getPanelAbsoluteLocation().x;
    int myY = e.getY() - boxInfo.getPanelAbsoluteLocation().y;
    return isWithinMyXRange(myX) && isWithinMyYRange(myY);
  }

  private boolean isWithinMyYRange(int myY)
  {
    return myY > 0 && myY < boxInfo.getPanelHeight();
  }

  private boolean isWithinMyXRange(int myX)
  {
    return myX > 0 && myX < boxInfo.getPanelWidth();
  }

  public int calculateMouseClickIndex(int x, int y)
  {
    TextHitInfo hitInfo = boxInfo.getTextLayout().hitTestChar(x + boxInfo.getXOffset(), y);
    int index = hitInfo.getCharIndex();
    if (isHitOnTheRightEdge(hitInfo, index))
      index += 1;
    return index;
  }

  private boolean isHitOnTheRightEdge(TextHitInfo hitInfo, int index)
  {
    return index < hitInfo.getInsertionIndex() && index == boxInfo.text.length() - 1;
  }

  public void processMousePressed(MouseEvent e)
  {
    if (isMouseEventInBox(e))
    {
      int myX = e.getX() - boxInfo.getPanelAbsoluteLocation().x;
      int myY = e.getY() - boxInfo.getPanelAbsoluteLocation().y;
      boxInfo.selectionOn = true;
      boxInfo.setSelectionIndex(calculateMouseClickIndex(myX, myY));
      boxInfo.setCursorIndex(boxInfo.getSelectionIndex());
      makeExtraSelectionOnMultiClick();
      lastClickTime = (new Date()).getTime();
    }
  }

  public void makeExtraSelectionOnMultiClick()
  {
    if (lastClickTime >= (new Date()).getTime() - 300)
    {
      if (doubleClickOn)
        selectAllOnTripleClick();
      else
        selectWordOnDoubleClick();
    }
    else
      doubleClickOn = false;
  }

  private void selectAllOnTripleClick()
  {
    boxInfo.setSelectionIndex(0);
    boxInfo.setCursorIndex(boxInfo.text.length());
  }

  private void selectWordOnDoubleClick()
  {
    boxInfo.setSelectionIndex(boxInfo.findWordsLeftEdge(boxInfo.getCursorIndex()));
    boxInfo.setCursorIndex(boxInfo.findWordsRightEdge(boxInfo.getCursorIndex()));
    doubleClickOn = true;
  }

  public void processMouseDragged(MouseEvent e)
  {
    int myX = e.getX() - boxInfo.getPanelAbsoluteLocation().x;
    int myY = e.getY() - boxInfo.getPanelAbsoluteLocation().y;
    int tempIndex = calculateMouseClickIndex(myX, myY);
    if (doubleClickOn)
      selectWord(tempIndex);
    else
      boxInfo.setCursorIndex(tempIndex);
  }

  private void selectWord(int tempIndex)
  {
    new WordSelector(tempIndex).processWordSelection();
  }

  public void processMouseReleased(MouseEvent e)
  {
    int myX = e.getX() - boxInfo.getPanelAbsoluteLocation().x;
    int myY = e.getY() - boxInfo.getPanelAbsoluteLocation().y;
    if (!doubleClickOn)
    {
      boxInfo.setCursorIndex(calculateMouseClickIndex(myX, myY));
      if (boxInfo.getCursorIndex() == boxInfo.getSelectionIndex())
        boxInfo.selectionOn = false;
    }
  }

  private class WordSelector
  {
    private int mouseIndex;

    public WordSelector(int mouseIndex)
    {
      this.mouseIndex = mouseIndex;
    }

    public void processWordSelection()
    {
      boolean rightOfTail = isRightOfTail();

      boolean selectionFacingRight = isSelectionFacingRight();

      boolean isMouseTrailingTheTail = selectionFacingRight && !rightOfTail || !selectionFacingRight && rightOfTail;
      if(isMouseTrailingTheTail)
        turnAround();

      if(rightOfTail)
        repositionHead(boxInfo.findWordsRightEdge(mouseIndex));
      else
        repositionHead(boxInfo.findWordsLeftEdge(mouseIndex));
    }

    private void turnAround()
    {
      boxInfo.setSelectionIndex(boxInfo.getCursorIndex());
    }

    private void repositionHead(int newHead)
    {
      boxInfo.setCursorIndex(newHead);
    }

    private boolean isSelectionFacingRight()
    {
      return boxInfo.cursorIndex > boxInfo.selectionIndex;
    }

    private boolean isRightOfTail()
    {
      return mouseIndex > boxInfo.selectionIndex;
    }
  }
}