package objects;

// ZL

public class Spaceship extends CosmicObjects 
{

	public Spaceship(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double ff, double dC) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		fuel = ff;
		dConsumption = dC;
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
	
	double fuel; // Fuel status in %%
	double dConsumption;
}
