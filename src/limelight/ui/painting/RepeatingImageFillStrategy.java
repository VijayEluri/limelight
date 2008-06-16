//- Copyright 2008 8th Light, Inc.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.painting;

import limelight.util.Box;

import java.awt.*;

public class RepeatingImageFillStrategy implements ImageFillStrategy
{
	public void fill(Graphics2D graphics, Image image)
	{
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		Box area = new Box(graphics.getClipBounds());

		for(int y = 0; y < area.height; y += imageHeight)
		{
			for(int x = 0; x < area.width; x += imageWidth)
			{
				graphics.drawImage(image, x, y, null);				
			}
		}
	}
}
