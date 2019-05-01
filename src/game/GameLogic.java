package game;

import java.awt.Color;
import java.awt.Graphics2D;

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
	
	public void update()
	{
		// Here will be calculations - can be done better
		// Probably threads will slow everything down
		CalculationThread planetThread[] = new CalculationThread[planetarySystem.length];
		for(int i = 1; i < planetThread.length; i++)
		{
			planetThread[i] = new CalculationThread(planetarySystem[i], planetarySystem, dt);
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
	
	public CelestialBody[] getPlanetarySystem()
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
	
	// Sets
	public void setDT(int newDT) 
	{
		dt = newDT;
	}
	
	private Color backgroundColor = Color.BLACK;
	private Spaceship ship;
	CelestialBody planetarySystem[]; // star and planets - maybe list would be better?
	int objectNumber; // number of celestial bodies in planetarySystem
	private int dt = HOUR; // DT in seconds!!!
	public final int initDT = dt;
	private int timeLeft = 700; // Only 700 sec?! Maybe will change to more
	
	public static final int HOUR = 3600, DAY = 24 * HOUR, MONTH = 30 * DAY, YEAR = 365 * DAY;
}
