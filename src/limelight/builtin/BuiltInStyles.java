//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.builtin;

import limelight.styles.RichStyle;
import limelight.util.Colors;

import java.util.HashMap;

public class BuiltInStyles
{
  private static HashMap<String, RichStyle> styles;

  public static HashMap<String, RichStyle> all()
  {
    if(styles != null)
      return styles;

    styles = new HashMap<String, RichStyle>();
    styles.put("limelight_builtin_curtains", buildCurtains());
    styles.put("limelight_builtin_drop_down_popup_list", buildDropDownPopupList());
    styles.put("limelight_builtin_drop_down_popup_list_item", buildDropDownPopupListItem());
    styles.put("limelight_builtin_drop_down_popup_list_item_selected", buildDropDownPopupListItemHover());

    return styles;
  }

  private static RichStyle buildCurtains()
  {
    RichStyle style = new RichStyle();
    style.setFloat("on");
    style.setX(0);
    style.setY(0);
    style.setWidth("100%");
    style.setHeight("100%");
    style.setBackgroundColor(Colors.toString(Colors.TRANSPARENT));
    return style;
  }

  private static RichStyle buildDropDownPopupList()
  {
    RichStyle style = new RichStyle();
    style.setFloat("on");
    style.setBackgroundColor("#EEED");
    style.setBorderWidth(1);
    style.setRoundedCornerRadius(5);
    style.setBorderColor("#dcdcdc");
    style.setVerticalScrollbar("on");
    style.setMinHeight(50);
    style.setMaxHeight(200);
    return style;
  }

  private static RichStyle buildDropDownPopupListItem()
  {
    RichStyle style = new RichStyle();
    style.setWidth("100%");
    style.setPadding(3);
    style.setLeftPadding(10);
    return style;
  }

  private static RichStyle buildDropDownPopupListItemHover()
  {
    RichStyle style = new RichStyle();
    style.setTextColor("white");
    style.setBackgroundColor("#bbd453");
    style.setSecondaryBackgroundColor("#9fb454");
    style.setGradientAngle(270);
    style.setGradient("on");
    return style;
  }
}
