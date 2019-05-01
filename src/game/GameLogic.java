package game;

import java.awt.Color;

import objects.CelestialBody;
import objects.Spaceship;

public class GameLogic 
{

	public GameLogic() 
	{
		// initialize ship and stuff
		ship = new Spaceship("Turbopogromca grawitacji", 3000000, 0, 0, 10, 40, 100);
	}
	
	// Gets
	public Spaceship getShip() 
	{
		return ship;
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
	public void setDT(double newDT) 
	{
		dt = newDT;
	}
	
	private Color backgroundColor = Color.black;
	private Spaceship ship;
	CelestialBody planetarySystem[]; // star and planets - maybe list would be better?
	int objectNumber; // number of celestial bodies in planetarySystem
	public final double initDT = 11;
	private double dt = 11;
	private int timeLeft = 700;
}
