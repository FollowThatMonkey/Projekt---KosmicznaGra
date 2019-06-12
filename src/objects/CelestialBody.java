package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import game.GameLogic;

// ZL

public class CelestialBody extends CosmicObjects 
{

	public CelestialBody(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double rad, Color col) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		radius = rad;
		color = col;
		setType(ObjectType.celestialBody);
	}
	
	// drawing celestialbody to buffImage
	public void draw(Graphics2D g2d, GameLogic logic)
	{
		g2d.setColor(color);
		double CamX = logic.getShip().getXPos()/logic.getScale()-(logic.getCurrentSize().getWidth()/2);
		double CamY = logic.getShip().getYPos()/logic.getScale()-(logic.getCurrentSize().getHeight()/2);
		int scaledPosX = (int) ((double) getXPos()/logic.getScale() - CamX);
		int scaledPosY = (int) ((double) getYPos()/logic.getScale() - CamY);
		double scaledRadius = radius / logic.getScale();
		g2d.fillOval(scaledPosX - (int)scaledRadius, scaledPosY + (int)scaledRadius, (int)scaledRadius, (int)scaledRadius);
		g2d.setColor(Color.RED);
		g2d.drawString(getName(), scaledPosX, scaledPosY);
	}
	
	// get planet radius
	public double getRadius() { return radius; }
	
	double radius;
	Color color;
}
