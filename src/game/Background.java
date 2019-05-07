package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Background
{
	public Background(Color bgColor)
	{
		color = bgColor;
		MAX_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		MAX_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(color);
		g2d.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
	}
	
	Color color;
	int MAX_WIDTH, MAX_HEIGHT;
}
