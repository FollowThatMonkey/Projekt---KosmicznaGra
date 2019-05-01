package objects;

// ZL

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
	
	// Sets
	public void setName(String nn)
	{
		name = nn;
	}
	
	public void setMass(double mm) 
	{
		 mass = mm;
	}
	
	public void setXPos(double xPos) 
	{
		 xPosition = xPos;
	}
	
	public void setXVel(double xVel) 
	{
		 xVelocity = xVel;
	}
	
	public void setYPos(double yPos) 
	{
		 yPosition = yPos;
	}
	
	public void setYVel(double yVel) 
	{
		 yVelocity = yVel;
	}
	
	private String name;
	private double mass;
	private double xPosition, xVelocity;
	private double yPosition, yVelocity;
	
	abstract void calculateVelocity();
	abstract void calculatePosition();
}
