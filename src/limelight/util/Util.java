//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Util
{
  public static String ENDL = System.getProperty("line.separator");

  public static boolean equal(Object o1, Object o2)
  {
    if(o1 != null)
      return o1.equals(o2);
    else if(o2 != null)
      return o2.equals(o1);
    else
      return true;
  }

  public static Map<String, Object> toMap(Object... args)
  {
    if(args.length % 2 == 1)
      throw new RuntimeException("toMap must be called with an even number of parameters");
    HashMap<String, Object> map = new HashMap<String, Object>();
    for(int i = 0; i < args.length; i += 2)
    {
      String key = "" + args[i];
      Object value = args[i + 1];
      map.put(key, value);
    }
    return map;
  }

  public static String mapToString(Map<String, Object> map)
  {
    LinkedList<String> pairs = new LinkedList<String>();
    for(String s : map.keySet())
      pairs.add(s + ": " + map.get(s));

    return "{" + StringUtil.join(", ", pairs.toArray()) + "}";
  }

  public static String toString(Object o)
  {
    return o == null ? null : o.toString();
  }
}
