package objects;

// ZL

public class Spaceship extends CosmicObjects 
{

	public Spaceship(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double ff) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		dConsumption = ff;
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
	
	double fuel = 100; // Fuel status in %%
	double dConsumption;
}
