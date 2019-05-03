package objects;

import java.awt.Color;
import java.awt.Graphics2D;

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
	
	public void draw(Graphics2D g2d, GameLogic logic)
	{
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int)logic.getCurrentSize().getWidth() / 2 - 20, (int)logic.getCurrentSize().getHeight() / 2 - 60, 40, 120);
		g2d.rotate(theta2, (int)logic.getCurrentSize().getWidth() / 2, (int)logic.getCurrentSize().getHeight() / 2);
		theta2 = 0;
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
				setXVel(getXVel() + Math.sin(theta) * engineThrust);
				setYVel(getYVel() + Math.cos(theta) * engineThrust);
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
			theta2 -= Math.PI / 60;
		}
			
	}
	
	public void rorateRight(boolean b)
	{
		if(b)
		{
			theta -= Math.PI / 60;
			theta2 += Math.PI / 60;
		}
			
	}

	//calculates spaceship's speed at the current moment
	public double speed() 
	{
		return Math.sqrt(Math.pow(getXVel(), 2) + Math.pow(getYVel(), 2));
	}
	
	public double getFuel() 
	{
		return fuel;
	}
	
	public void setDConsumption(double dC)
	{
		dConsumption = dC;
	}
	
	public double getTheta()
	{
		return theta;
	}

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
	
	private double engineThrust = 100;
	// Deegrees to X axis
	private double theta = Math.PI / 2, theta2 = 0;
	private double fuel = 100; // Fuel status in %%
	private double dConsumption;
}
