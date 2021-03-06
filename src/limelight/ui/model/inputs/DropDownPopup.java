//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model.inputs;

import limelight.events.Event;
import limelight.events.EventAction;
import limelight.styles.RichStyle;
import limelight.ui.Panel;
import limelight.ui.SimplePropProxy;
import limelight.ui.events.panel.MouseClickedEvent;
import limelight.ui.events.panel.MouseEnteredEvent;
import limelight.ui.events.panel.PanelEvent;
import limelight.ui.model.PropPanel;
import limelight.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DropDownPopup
{
  private final DropDownPanel dropDown;
  private PropPanel curtains;
  private PropPanel popupList;
  private PropPanel selectedItem;
  private Map<String, RichStyle> stylesStore;

  public DropDownPopup(final DropDownPanel dropDownPanel)
  {
    dropDown = dropDownPanel;

    stylesStore = dropDown.getRoot().getStyles();
    createCurtains();
    createList();
    createListItems();

    curtains.add(popupList);

    dropDown.setPopup(this);
  }

  private void createListItems()
  {
    EventAction itemChosenAction = new EventAction()
    {
      public void invoke(Event e)
      {
        PanelEvent event = (PanelEvent)e;
        choose((PropPanel) event.getRecipient());
      }
    };

    EventAction itemSelectedAction = new EventAction()
    {
      public void invoke(Event e)
      {
        PanelEvent event = (PanelEvent)e;
        select((PropPanel) event.getRecipient());
      }
    };

    for(Object option : dropDown.getChoices())
    {
      PropPanel listItem = new PropPanel(new SimplePropProxy(), Util.toMap("name", "limelight_builtin_drop_down_popup_list_item"));
      listItem.getStyle().addExtension(stylesStore.get("limelight_builtin_drop_down_popup_list_item"));
      listItem.getEventHandler().add(MouseClickedEvent.class, itemChosenAction);
      listItem.getEventHandler().add(MouseEnteredEvent.class, itemSelectedAction);
      listItem.setText(option.toString());

      if(option.equals(dropDown.getSelectedChoice()))
        select(listItem);

      popupList.add(listItem);
    }
  }

  private void select(PropPanel listItem)
  {
    if(selectedItem == listItem)
      return;

    if(selectedItem != null)
      selectedItem.getStyle().removeScreen();

    selectedItem = listItem;
    selectedItem.getStyle().removeScreen();
    selectedItem.getStyle().applyScreen(stylesStore.get("limelight_builtin_drop_down_popup_list_item_selected"));

    // TODO - MDM - Need a better way to handle this... screens conflicts with hover.
  }

  private void createList()
  {
    popupList = new PropPanel(new SimplePropProxy(), Util.toMap("name", "limelight_builtin_drop_down_popup_list"));
    popupList.getStyle().addExtension(stylesStore.get("limelight_builtin_drop_down_popup_list"));
    popupList.getStyle().setX(dropDown.getParent().getAbsoluteLocation().x - dropDown.getRoot().getX());
    popupList.getStyle().setY(dropDown.getParent().getAbsoluteLocation().y - dropDown.getRoot().getY());
    popupList.getStyle().setWidth(dropDown.getParent().getWidth());
    popupList.getEventHandler().add(MouseClickedEvent.class, new EventAction()
    {
      public void invoke(Event event)
      {
        // eat the event so the curtains won't get it
      }
    });
  }

  private void createCurtains()
  {
    curtains = new PropPanel(new SimplePropProxy(), Util.toMap("name", "limelight_builtin_curtains"));
    curtains.getStyle().addExtension(stylesStore.get("limelight_builtin_curtains"));
    curtains.getEventHandler().add(MouseClickedEvent.class, new EventAction()
    {
      public void invoke(Event event)
      {
        close();
      }
    });
  }

  public void open()
  {
    dropDown.getRoot().add(curtains);
  }

  public void close()
  {
    curtains.getParent().remove(curtains);
    dropDown.setPopup(null);
  }

  public void choose(PropPanel item)
  {
    if(item != null)
      dropDown.setText(item.getText());
    close();
  }

  public PropPanel getSelectedItem()
  {
    return selectedItem;
  }

  public void selectNext()
  {
    final List<PropPanel> items = getListItems();
    int selectedIndex = selectedItem == null ? -1 : selectedIndex(items);
    if(selectedIndex < (items.size() - 1))
      select(items.get(selectedIndex + 1));
  }

  private List<PropPanel> getListItems()
  {
    List<PropPanel> items = new ArrayList<PropPanel>();
    for(Panel panel : popupList.getChildren())
    {
      if(panel instanceof PropPanel)
        items.add((PropPanel)panel);
    }
    return items;
  }

  public void selectPrevious()
  {
    final List<PropPanel> items = getListItems();
    int selectedIndex = selectedItem == null ? items.size() : selectedIndex(items);
    if(selectedIndex > 0)
      select(items.get(selectedIndex - 1));
  }

  private int selectedIndex(List<PropPanel> items)
  {
    int selectedIndex = 0;
    for(int i = 0; i < items.size(); i++)
    {
      if(selectedItem == items.get(i))
      {
        selectedIndex = i;
        break;
      }
    }
    return selectedIndex;
  }

  public PropPanel getPopupList()
  {
    return popupList;
  }
}
