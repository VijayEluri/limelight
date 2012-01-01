//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.util;

import java.awt.*;

public class Box extends java.awt.Rectangle
{
	public Box(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Box(java.awt.Rectangle r)
	{
		x = r.x;
		y = r.y;
		width = r.width;
		height = r.height;
	}

	public void shave(int top, int right, int bottom, int left)
	{
		y = y + top;
		x = x + left;
		width = width - left - right;
		height = height - top - bottom;
	}

	public int top()
	{
		return y;
	}

	public int right()
	{
		return x + width - 1;
	}

	public int bottom()
	{
		return y + height - 1;
	}

	public int left()
	{
		return x;
	}

	public String toString()
	{
		return "Box: x: " + x + ",  y: " + y + ", width: " + width + ", height: " + height;
	}

  public boolean sameSize(Rectangle other)
  {
    return other != null && width == other.width && height == other.height;
  }

  public Box translated(Point offset)
  {
    translate(offset.x, offset.y);
    return this;
  }

  public Box translated(int x, int y)
  {
    translate(x, y);
    return this;
  }
}
