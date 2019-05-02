package objects;

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
	
	double fuel = 100; // Fuel status in %%
	double dConsumption;
}
