package objects;

public class Spaceship extends CosmicObjects {

	public Spaceship(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double ff) {
		super(nn, mm, xPos, yPos, xVel, yVel);
		fuel = ff;
	}
	
	@Override
	void calculateVelocity() {
		// TODO Auto-generated method stub

	}

	@Override
	void calculatePosition() {
		// TODO Auto-generated method stub

	}
	
	public double speed() {
		return Math.sqrt(Math.pow(getXVel(), 2) + Math.pow(getYVel(), 2));
	}
	
	public double getFuel() {
		return fuel;
	}
	
	double fuel; // Fuel status in %%
}
