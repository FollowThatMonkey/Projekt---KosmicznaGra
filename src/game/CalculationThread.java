package game;

import java.util.List;

import objects.CelestialBody;
import objects.CosmicObjects;
import objects.CosmicObjects.ObjectType;

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
		// counting forces etc. for planets from sun
		if(currentObj.getType() == ObjectType.celestialBody)
		{
			currentObj.calcAcc(objArray.get(0));
			currentObj.calculateVelocity(dt);
			currentObj.calculatePosition(dt);
		}
		if(currentObj.getName().equals("Ziemia"))
			System.out.println(System.currentTimeMillis() + " " + currentObj.getXPos() + " " + currentObj.getYPos());
			//System.out.println("Name: " + currentObj.getName() + ",\tXpos, yPos: " + currentObj.getXPos() + "\t" + currentObj.getYPos());
		
	}
	
	private CosmicObjects currentObj;
	private List<CelestialBody> objArray;
	private int dt;
}
