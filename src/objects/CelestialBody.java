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
		//int scaledPosX = (int)(logic.getShip().getXPos() - getXPos()) / logic.getScale() + (int)(logic.getCurrentSize().getWidth() / 2);
		//int scaledPosY = (int)(logic.getShip().getYPos() - getYPos()) / logic.getScale() + (int)(logic.getCurrentSize().getHeight() / 2);
		double CamX = logic.getShip().getXPos()/logic.getScale()-(logic.getCurrentSize().getWidth()/2);
		double CamY = logic.getShip().getYPos()/logic.getScale()-(logic.getCurrentSize().getHeight()/2);
		int scaledPosX = (int) ((double) getXPos()/logic.getScale() - CamX);
		int scaledPosY = (int) ((double) getYPos()/logic.getScale() - CamY);
//<<<<<<< HEAD
		//double scaledRadius = radius / logic.getScale();
		//double scaledRadius = 200*radius / logic.getScale();
		//g2d.fillOval(scaledPosX, scaledPosY, (int)scaledRadius, (int)scaledRadius);
//=======
		double scaledRadius = radius / logic.getScale();
		g2d.fillOval(scaledPosX - (int)scaledRadius, scaledPosY - (int)scaledRadius, (int)scaledRadius, (int)scaledRadius);
//>>>>>>> refs/remotes/origin/rafal
		g2d.setColor(Color.RED);
		g2d.drawString(getName(), scaledPosX, scaledPosY);
		if(Math.abs(logic.getShip().getXPos() - getXPos()) < radius && Math.abs(logic.getShip().getYPos() - getYPos()) < radius)
			System.out.println("Coś nie działa!!");
		//System.out.println("ScaledPos and radius\t" + scaledPosX + "\t" + scaledPosY + "\t" + scaledRadius);
	}
	
	double radius;
}
