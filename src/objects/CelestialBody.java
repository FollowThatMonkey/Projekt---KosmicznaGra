package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import game.GameLogic;

// ZL

public class CelestialBody extends CosmicObjects 
{

	public CelestialBody(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double rad) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		radius = rad;
		setType(ObjectType.celestialBody);
	}
	
	public void draw(Graphics2D g2d, GameLogic logic)
	{
		g2d.setColor(Color.BLUE);
		int scaledPosX = (int)(logic.getShip().getXPos() - getXPos()) / logic.getScale() + (int)(logic.getCurrentSize().getWidth() / 2);
		int scaledPosY = (int)(logic.getShip().getYPos() - getYPos()) / logic.getScale() + (int)(logic.getCurrentSize().getHeight() / 2);
		double scaledRadius = radius / logic.getScale();
		//double scaledRadius = radius;
		g2d.fillOval(scaledPosX, scaledPosY, (int)scaledRadius, (int)scaledRadius);
		g2d.setColor(Color.RED);
		g2d.drawString(getName(), scaledPosX, scaledPosY);
		if(Math.abs(logic.getShip().getXPos() - getXPos()) < radius && Math.abs(logic.getShip().getYPos() - getYPos()) < radius)
			System.out.println("Coś nie działa!!");
		System.out.println("ScaledPos and radius\t" + scaledPosX + "\t" + scaledPosY + "\t" + scaledRadius);
	}
	
	double radius;
}
