package objects;

// ZL

public class CelestialBody extends CosmicObjects 
{

	public CelestialBody(String nn, double mm, double xPos, double yPos, double xVel, double yVel) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		setType(ObjectType.celestialBody);
	}
}
