package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import game.GameLogic;
import objects.CosmicObjects.ObjectType;

// ZL

public class Spaceship extends CosmicObjects 
{

	public Spaceship(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double dC) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
		setType(ObjectType.spaceship);
	}
	
	// draw spaceship to buffImage
	public void draw(Graphics2D g2d, GameLogic logic)
	{
		BufferedImage tempImage = new BufferedImage((int)logic.getCurrentSize().getWidth(), (int)logic.getCurrentSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tempImage.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect((int)logic.getCurrentSize().getWidth() / 2 - 7, (int)logic.getCurrentSize().getHeight() / 2 - 20, 15, 40);
		g.setColor(Color.RED);
		g.fillOval((int)logic.getCurrentSize().getWidth() / 2 - 4, (int)logic.getCurrentSize().getHeight() / 2 - 20, 8, 8);
		g.rotate(- (theta - Math.PI / 2), (int)logic.getCurrentSize().getWidth() / 2, (int)logic.getCurrentSize().getHeight() / 2);
		
		g2d.drawImage(tempImage, g.getTransform(), null);
	}
	
	//Movement functions
	public void moveUp(boolean b)
	{
		if(b)
		{
			// movement available only if fuel > 0
			if(fuel > 0)
			{
				fuel -= dConsumption;
				setXVel(getXVel() + Math.cos(theta) * engineThrust);
				setYVel(getYVel() - Math.sin(theta) * engineThrust);
				setMass(getMass() - dConsumption * 10000);
			}
			if(fuel < 0)
				fuel = 0;
		}
	}
	
	public void rotateLeft(boolean b)
	{
		if(b)
		{
			theta += Math.PI / 60;
		}
			
	}
	
	public void rorateRight(boolean b)
	{
		if(b)
		{
			theta -= Math.PI / 60;
		}
			
	}

	//calculates spaceship's speed at the current moment
	public double speed() 
	{
		return Math.sqrt(Math.pow(getXVel(), 2) + Math.pow(getYVel(), 2)) / 1000;
	}
	
	// Gets
	public double getFuel() { return fuel; }
	
	public void setDConsumption(double dC) { dConsumption = dC; }
	
	public double getTheta() { return theta; }
	
	// Sets
	public void setParameters(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double dC) 
	{
		super.setParameters(nn, mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
	}
	
	public void setParameters(double mm, double xPos, double yPos, double xVel, double yVel, double dC)
	{
		super.setParameters(mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
	}
	
	
	
	private double engineThrust = 10000;
	// Deegrees to X axis
	private double theta = Math.PI / 2;
	private double fuel = 100; // Fuel status in %%
	private double dConsumption;
}
