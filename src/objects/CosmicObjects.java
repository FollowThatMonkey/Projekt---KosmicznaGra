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
	
	// Calculate acceleration
	public void calcAcc(CosmicObjects obj)
	{
		double r = Math.sqrt(Math.pow(obj.getXPos() - xPosition, 2) + Math.pow(obj.getYPos() - yPosition, 2));
		double acceleration = -G * obj.getMass() / Math.pow(r, 2);
		double phi = Math.atan2(yPosition - obj.getYPos(), xPosition - getXPos());
		
		xAcc = Math.cos(phi) * acceleration;
		yAcc = Math.sin(phi) * acceleration;
	}
	
	
	public void calculateVelocity(int dt) 
	{
		xVelocity += xAcc * dt;
		yVelocity += yAcc * dt;
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
	
	public ObjectType getType()
	{
		return type;
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
	
	public void setType(ObjectType newType)
	{
		type = newType;
	}
	
	public void setParameters(String nn, double mm, double xPos, double yPos, double xVel, double yVel)
	{
		name = nn;
		mass = mm;
		xPosition = xPos;
		yPosition = yPos;
		xVelocity = xVel;
		yVelocity = yVel;
	}
	
	public void setParameters(double mm, double xPos, double yPos, double xVel, double yVel) //without name
	{
		mass = mm;
		xPosition = xPos;
		yPosition = yPos;
		xVelocity = xVel;
		yVelocity = yVel;
	}
	
	private String name;
	private double mass;
	private double xPosition, xVelocity;
	private double yPosition, yVelocity;
	private double xAcc, yAcc;
	private ObjectType type = ObjectType.UFO;
	
	// Gravitational constant
	public final static double G = 6.67e-11;

	// Enum to see what type of object it is
	public enum ObjectType
	{
		celestialBody,
		spaceship,
		UFO
	}
}
