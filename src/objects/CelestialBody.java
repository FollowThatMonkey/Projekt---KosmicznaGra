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
	
	public void draw(Graphics2D g2d, Spaceship ship)
	{
		g2d.setColor(Color.BLUE);
		g2d.fillOval((int)(ship.getXPos() - getXPos()), (int)(ship.getYPos() - getYPos()), (int)radius, (int)radius);
		if(Math.abs(ship.getXPos() - getXPos()) < radius && Math.abs(ship.getYPos() - getYPos()) < radius)
			System.out.println("Coś nie działa!!");
	}
	
	double radius;
}
