package game;

import objects.CosmicObjects;

public class CalculationThread extends Thread
{
	public CalculationThread(CosmicObjects obj, CosmicObjects objArray[], int setDT)
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
		for(CosmicObjects iterator : objArray)
		{
			if(currentObj != iterator)
			{
				double xForce = 0, yForce = 0;
				currentObj.calcForce(iterator, xForce, yForce);
				currentObj.calculateVelocity(xForce, yForce, dt);
				currentObj.calculatePosition(dt);
			}
		}
		System.out.println("Name: " + currentObj.getName() + "Xpos, yPos:" + currentObj.getXPos() + " " + currentObj.getYPos());
		
	}
	
	private CosmicObjects currentObj, objArray[];
	private int dt;
}
