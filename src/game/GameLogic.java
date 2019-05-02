package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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
		ship = new Spaceship("Turbopogromca grawitacji", 3000000, 0, 0, 10, 40, 0.008);
	}
	
	// making threads
	private void objectThreads()
	{
		// Creating threads for planets' calculations and starting them
		CalculationThread planetsThreads[] = new CalculationThread[planetarySystem.size() - 1];
		CalculationThread shipThread = new CalculationThread(ship, planetarySystem, dt);
		for(int i = 0; i < planetsThreads.length; i++)
			planetsThreads[i] = new CalculationThread(planetarySystem.get(i + 1), planetarySystem, dt);
		for(CalculationThread iterator : planetsThreads)
			iterator.start();
		shipThread.start();
		for(CalculationThread iterator : planetsThreads)
		{
			try
			{
				iterator.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			shipThread.join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update()
	{
		
		// Here will be calculations - can be done better
		// Probably threads will slow everything down
		objectThreads();
		
		// Create similar thread for the spaceship!
		
	}
	
	public void draw(Graphics2D g2d)
	{
		// Here will be drawing to buffImage
	}
	
	public void keyPressed(int key)
	{
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			ship.moveUp(true);
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			ship.rotateLeft(true);
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			ship.rorateRight(true);
		
	}
	
	public void keyReleased(int key)
	{
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			ship.moveUp(false);
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			ship.rotateLeft(false);
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			ship.rorateRight(false);
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
	private int dt = DAY / 15; // DT in seconds!!!
	public final int initDT = dt;
	private int timeLeft = 700; // Only 700 sec?! Maybe will change to more
	
	// Global constants
	public static final int HOUR = 3600, DAY = 24 * HOUR, MONTH = 30 * DAY, YEAR = 365 * DAY;
}
