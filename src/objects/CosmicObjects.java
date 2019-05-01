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
	
	public void calcForce(CosmicObjects obj, double xForce, double yForce)
	{
		xForce = -G * obj.getMass() / Math.pow(obj.getXPos() - xPosition, 2);
		yForce = -G * obj.getMass() / Math.pow(obj.getYPos() - yPosition, 2);
	}
	
	public void calculateVelocity(double xForce, double yForce, int dt) 
	{
		xVelocity += xForce * dt;
		yVelocity += yForce * dt;
	}
	
	public void calculatePosition(int dt)
	{
		xPosition += xVelocity * dt;
		yPosition += yVelocity * dt;
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
	
	// Gravitational constant
	public final static double G = 6.67e-11;
}
