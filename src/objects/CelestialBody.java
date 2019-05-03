package objects;

// ZL

public class CelestialBody extends CosmicObjects 
{

	public CelestialBody(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double rad) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		radius = rad;
		setType(ObjectType.celestialBody);
	}
	
	double radius;
}
