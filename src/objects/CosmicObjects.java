package objects;

public abstract class CosmicObjects 
{

	public CosmicObjects(String nn, double mm, double xPos, double yPos, double xVel, double yVel) 
	{
		name = nn;
		mass = mm;
		xPosition = xPos;
		yPosition = yPos;
		xVelocity = xVel;
		yVelocity = yVel;
	}
	
	// Gets
	public String getName() 
	{
		return name;
	}
	
	public double getMass() 
	{
		return mass;
	}
	
	public double getXPos() 
	{
		return xPosition;
	}
	
	public double getXVel() 
	{
		return xVelocity;
	}
	
	public double getYPos() 
	{
		return yPosition;
	}
	
	public double getYVel() 
	{
		return yVelocity;
	}
	
	private String name;
	private double mass;
	private double xPosition, xVelocity;
	private double yPosition, yVelocity;
	
	abstract void calculateVelocity();
	abstract void calculatePosition();
}
