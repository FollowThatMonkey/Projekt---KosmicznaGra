package objects;

public abstract class CosmicObjects {

	public CosmicObjects(String nn, double mm, double xPos, double yPos, double xVel, double yVel) {
		name = nn;
		mass = mm;
		xPosition = xPos;
		yPosition = yPos;
		xVelocity = xVel;
		yVelocity = yVel;
	}
	
	// Gets
	String getName() {
		return name;
	}
	
	double getMass() {
		return mass;
	}
	
	double getXPos() {
		return xPosition;
	}
	
	double getXVel() {
		return xVelocity;
	}
	
	double getYPos() {
		return yPosition;
	}
	
	double getYVel() {
		return yVelocity;
	}
	
	private String name;
	private double mass;
	private double xPosition, xVelocity;
	private double yPosition, yVelocity;
	
	abstract void calculateVelocity();
	abstract void calculatePosition();
}
