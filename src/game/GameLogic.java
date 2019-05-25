package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import objects.CelestialBody;
import objects.CosmicObjects;
import objects.Spaceship;
import windows.MainFrame;
import windows.RightPanel;

// ZL & RJ?

public class GameLogic
{

	public GameLogic() 
	{
		// initialize ship and stuff
		ship = new Spaceship("", 3000000, 0, 0, 10, 40, 0.008);
		
		// Setting background
		background = new Background(backgroundColor);
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
	
	// doing calculations of positions etc.
	public void update()
	{
		if(!gameOver)
		{
			objectThreads();
			ship.update();
			
			if(ship.getFuel() == 0)
				enableGameOverButtons();
			
			if(timeLeft == 0)
				gameOver = true;
		}
		else
		{
			// To add gameOver things!!
			enableGameOverButtons();
		}
		
	}
	
	// drawing everything to buffImage
	public void draw(Graphics2D g2d)
	{
		// Here will be drawing to buffImage
		if(!gameOver)
		{
			SetClosestBody();
			background.draw(g2d);
			for(CelestialBody iterator : planetarySystem)
				iterator.draw(g2d, this);
			ship.draw(g2d, this);
		}
		else
		{
			background.draw(g2d);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Game Over!", getCurrentSize().width / 2, getCurrentSize().height / 2);
		}
		
	}
	
	// check which planet is the closest to the spaceship
	public void SetClosestBody()
	{
		if(closestBody == null)
			closestBody = planetarySystem.get(0);
		for(int i = 0; i < planetarySystem.size(); i++)
			if(Math.pow(planetarySystem.get(i).getXPos() - ship.getXPos(), 2) + Math.pow(planetarySystem.get(i).getYPos() - ship.getYPos(), 2) < Math.pow(closestBody.getXPos() - ship.getXPos(), 2) + Math.pow(closestBody.getYPos() - ship.getYPos(), 2))
				closestBody = planetarySystem.get(i);
	}
	
	// change scale dynamically
	/*
	private void changeScale()
	{
		double currentDistance = Math.sqrt( Math.pow(planetarySystem.get(0).getXPos() - ship.getXPos(), 2) + Math.pow(planetarySystem.get(0).getYPos() - ship.getYPos(), 2) );
		for(int i = 1; i < planetarySystem.size(); i++)
		{
			double tempDistance = Math.sqrt( Math.pow(planetarySystem.get(i).getXPos() - ship.getXPos(), 2) + Math.pow(planetarySystem.get(i).getYPos() - ship.getYPos(), 2) );
			if(tempDistance < currentDistance)
				currentDistance = tempDistance;
		}
		if(currentDistance < finalDisntace && currentDistance > minDistance)
		{
			scale = (long) ((maxScale - minScale) / (finalDisntace - minDistance) * currentDistance + (maxScale - (maxScale - minScale) / (finalDisntace - minDistance) * finalDisntace));
		}
	}
	*/
	
	// enabling restart and end-game buttons
	public void enableGameOverButtons()
	{
		rightPanel.getRestartButton().setEnabled(true);
		rightPanel.getEndButton().setEnabled(true);
	}
	
	// steering spaceship
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
	public Spaceship getShip() { return ship; }
	
	public List<CelestialBody> getPlanetarySystem() { return planetarySystem; }
	
	public double getDT() { return dt; }
	
	public int getTimeLeft() { return timeLeft; }
	
	public int getObjectNumber() { return objectNumber; }
	
	public Dimension getCurrentSize() { return size; }
	
	public long getScale() { return scale; }
	
	public boolean getGameOver() { return gameOver; }
	
	public CelestialBody getClosestBody() { return closestBody; }
	
	// Sets
	public void setDT(int newDT) { dt = newDT; }
	
	public void setObjectNumber(int N) { objectNumber = N; }
	
	public void setTimeLeft(int newTimeLeft) { timeLeft = newTimeLeft; }
	
	public void setCurrentSize(Dimension size) { this.size = size; }
	
	public void setScale(int scale) { this.scale = scale; }
	
	public void setRightPanel(RightPanel rightPanel) { if(this.rightPanel == null) this.rightPanel = rightPanel; }
	
	public void setGameOver(boolean newGameOver) {gameOver = newGameOver;}
	
	private Background background;
	private Dimension size;
	//private long scale = 50000000L;
	private long scale = 50L;
	
	private Color backgroundColor = Color.BLACK;
	private Spaceship ship;
	private List<CelestialBody> planetarySystem = new ArrayList<CelestialBody>(); // star and planets
	private int objectNumber; // number of celestial bodies in planetarySystem (planets + star)
	//private int dt = DAY * 2 / 60; // DT in seconds!!! -- one sec is one month
	private int dt = HOUR * 2 / 60; // DT in seconds!!! -- one sec is one month
	public final int initDT = dt;
	private int timeLeft = 6000; // Only 700 sec?! Maybe will change to more
	private boolean gameOver = false;
	private RightPanel rightPanel = null;
	private CelestialBody closestBody = null;
	
	// Global constants
	public static final int MINUTE = 60, HOUR = 3600, DAY = 24 * HOUR, MONTH = 30 * DAY, YEAR = 365 * DAY;
}
