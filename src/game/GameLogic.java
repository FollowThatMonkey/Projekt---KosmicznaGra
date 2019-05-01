package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import objects.CelestialBody;
import objects.CosmicObjects;
import objects.Spaceship;

// ZL & RJ?

public class GameLogic
{

	public GameLogic() 
	{
		// initialize ship and stuff
		ship = new Spaceship("Turbopogromca grawitacji", 3000000, 0, 0, 10, 40, 100);
	}
	
	// set threads for future calculations
	public void setThreads()
	{
		int i = 1;
		planetThread = new CalculationThread[planetarySystem.size() - 1];
		for(CalculationThread iterator : planetThread)
		{
			iterator = new CalculationThread(planetarySystem.get(i), planetarySystem, dt);
			i++;
			System.out.println("Set thread for planet nr: " + iterator.getName());
		}
	}
	
	public void update()
	{
		// Here will be calculations - can be done better
		// Probably threads will slow everything down
		for(CalculationThread iterator : planetThread)
			iterator.start();
		for(CalculationThread iterator : planetThread)
		{
			try
			{
				iterator.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		// Create similar thread for the spaceship!
		
	}
	
	public void draw(Graphics2D g2d)
	{
		// Here will be drawing to buffImage
	}
	
	public void keyPressed(int key)
	{
		
	}
	
	public void keyReleased(int key)
	{
		
	}
	
	// Gets
	public Spaceship getShip() 
	{
		return ship;
	}
	
	public List<CelestialBody> getPlanetarySystem()
	{
		return planetarySystem;
	}
	public double getDT() 
	{
		return dt;
	}
	
	public int getTimeLeft() 
	{
		return timeLeft;
	}
	
	public int getObjectNumber()
	{
		return objectNumber;
	}
	// Sets
	public void setDT(int newDT) 
	{
		dt = newDT;
	}
	
	public void setObjectNumber(int N)
	{
		objectNumber = N;
	}
	
	private Color backgroundColor = Color.BLACK;
	private Spaceship ship;
	private List<CelestialBody> planetarySystem = new ArrayList<CelestialBody>(); // star and planets - maybe list would be better?
	private int objectNumber; // number of celestial bodies in planetarySystem (planets + star)
	private int dt = HOUR; // DT in seconds!!!
	public final int initDT = dt;
	private int timeLeft = 700; // Only 700 sec?! Maybe will change to more
	
	private CalculationThread planetThread[];
	private CalculationThread rocketThread;
	
	// Global constants
	public static final int HOUR = 3600, DAY = 24 * HOUR, MONTH = 30 * DAY, YEAR = 365 * DAY;
}
