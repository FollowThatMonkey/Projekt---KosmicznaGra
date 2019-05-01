package game;

import java.util.List;

import objects.CelestialBody;
import objects.CosmicObjects;

public class CalculationThread extends Thread
{
	public CalculationThread(CosmicObjects obj, List<CelestialBody> objArray, int setDT)
	{
		this.objArray = objArray;
		currentObj = obj;
		dt = setDT;
	}
	
	// Calculating forces, vel, and pos for one planet
	@Override
	public void run()
	{
		super.run();
		for(CelestialBody iterator : objArray)
		{
			if(currentObj != iterator)
			{
				double xForce = 0, yForce = 0;
				currentObj.calcForce(iterator, xForce, yForce);
				currentObj.calculateVelocity(xForce, yForce, dt);
				currentObj.calculatePosition(dt);
			}
		}
		System.out.println("Name: " + currentObj.getName() + ",\tXpos, yPos: " + currentObj.getXPos() + "\t" + currentObj.getYPos());
		
	}
	
	private CosmicObjects currentObj;
	private List<CelestialBody> objArray;
	private int dt;
}
