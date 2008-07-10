//- Copyright 2008 8th Light, Inc.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles;

import limelight.util.Util;

import java.util.LinkedList;

public class RichStyle extends Style implements StyleObserver
{
  private String[] styles;
  private LinkedList<RichStyle> extensions;

  public RichStyle()
  {
    styles = new String[STYLE_COUNT];
  }

  public String get(int key)
  {
    if(styles[key] != null)
      return styles[key];
    else if(extensions != null)
    {
      return getFrom(extensions, key);
    }

    return null;
  }

  public void put(StyleDescriptor descriptor, String value)
  {
    if(value == null)
      return;
    value = value.trim();
    if(value.length() == 0)
      value = null;

    String originalValue = styles[descriptor.index];
    styles[descriptor.index] = value;
    if(!Util.equal(originalValue, value))
    {
      changes[descriptor.index] = true;
      notifyObserversOfChange(descriptor, value);
    }
  }

  protected boolean has(int key)
  {
    return styles[key] != null;
  }

  public void removeExtension(RichStyle extension)
  {
    if(extensions != null)
    {
      extension.removeObserver(this);
      applyChangesFromExtension(extension);
      extensions.remove(extension);
    }
  }

  public void addExtension(RichStyle extension)
  {
    if(extensions == null)
      extensions = new LinkedList<RichStyle>();

    if(extension != null && !hasExtension(extension))
    {
      applyChangesFromExtension(extension);
      extensions.add(extension);
      if(!extension.hasObserver(this))
        extension.addObserver(this);
    }
  }

  public void styleChanged(StyleDescriptor descriptor, String value)
  {
    if(styles[descriptor.index] == null)
    {
      changes[descriptor.index] = true;
      notifyObserversOfChange(descriptor, value);
    }
  }

  private void applyChangesFromExtension(RichStyle style)
  {
    LinkedList<RichStyle> seniorExtensions = findSeniorExtensions(style);

    for(StyleDescriptor descriptor : STYLE_LIST)
    {
      String value = style.get(descriptor.index);
      if(value != null && getFrom(seniorExtensions, descriptor.index) == null)
        styleChanged(descriptor, value);
    }
  }

  private LinkedList<RichStyle> findSeniorExtensions(RichStyle style)
  {
    LinkedList<RichStyle> seniorExtensions = new LinkedList<RichStyle>();
    for(RichStyle extension : extensions)
    {
      if(extension == style)
        break;
      seniorExtensions.add(extension);
    }
    return seniorExtensions;
  }

  private String getFrom(LinkedList<RichStyle> extensions, int key)
  {
    for(Style style : extensions)
    {
      String value = style.get(key);
      if(value != null)
        return value;
    }
    return null;
  }

  public boolean hasExtension(RichStyle style)
  {
    return extensions != null && extensions.contains(style);
  }
}
