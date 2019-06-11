package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

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
	
	public GameLogic(MainFrame frame)
	{
		// Links old window with new GameLogic
		this.frame = frame;
		
		gameOver = true;
		
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
			setScale();
			checkCollision();
			
			if(ship.getFuel() == 0)
				enableGameOverButtons(true);
			
			if(timeLeft == 0)
				gameOver = true;
		}
		else
		{
			// To add gameOver things!!
			enableGameOverButtons(true);
		}
		
	}
	
	// drawing everything to buffImage
	public void draw(Graphics2D g2d)
	{
		// Here will be drawing to buffImage
		if(!gameOver)
		{
			setClosestBody();
			background.draw(g2d);
			for(CelestialBody iterator : planetarySystem)
				iterator.draw(g2d, this);
			ship.draw(g2d, this);
		}
		else if(!success) // show just gameOver screen
		{
			background.draw(g2d);
			g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), 30));
			g2d.setColor(Color.WHITE);
			g2d.drawString(frame.windowBundle.getString("gameOver"), getCurrentSize().width / 2 - 70, getCurrentSize().height / 2);
		} else // show success screen - gameOver with positive result - or whatever
		{
			background.draw(g2d);
			g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), 30));
			g2d.setColor(Color.WHITE);
			g2d.drawString(frame.windowBundle.getString("success"), getCurrentSize().width / 2 - 70, getCurrentSize().height / 2);
		}
		
	}
	
	// dynamically changes scale to make navigation easier
	private void setScale()
	{
		if(getClosestBodyDistance() <= initDistnace / 4 && rocketDist != CurrDist.one)
		{
			rocketDist = CurrDist.one;
			setDT(45 * SECOND / 60);
			frame.getRightPanel().getTimeSlider().setValue((int) -(initDT - getDT()));
		}
		if(getClosestBodyDistance() <= 2 * initDistnace / 4 && getClosestBodyDistance() > initDistnace / 4 && rocketDist != CurrDist.two)
		{
			rocketDist = CurrDist.two;
			setDT(30 * MINUTE / 60);
			frame.getRightPanel().getTimeSlider().setValue((int) -(initDT - getDT()));
			
		}
		if(getClosestBodyDistance() <= 3 * initDistnace / 4 && getClosestBodyDistance() > 2 * initDistnace / 4 && rocketDist != CurrDist.three)
		{
			rocketDist = CurrDist.three;
			setDT(HOUR / 60);
			frame.getRightPanel().getTimeSlider().setValue((int) -(initDT - getDT()));
			
		}
		if(getClosestBodyDistance() > 4 * initDistnace / 4 && rocketDist != CurrDist.four)
		{
			rocketDist = CurrDist.four;
			setDT(2 * HOUR / 60);
			frame.getRightPanel().getTimeSlider().setValue((int) -(initDT - getDT()));
		}
	}
	
	// check if rocket landed on celestialBody
	private void checkCollision()
	{
		if(getClosestBodyDistance() <= closestBody.getRadius())
		{
			// if the relative to the closes body
			// velocity is greater than 5 km/s
			// then the gameOver happens
			// otherwise it's a successful landing
			if(getClosestBodyVel() >= 5e3)
				gameOver = true;
			else
			{
				gameOver = true;
				success = true;
			}
		}
	}
	
	// gets the distance to the closest body
	private double getClosestBodyDistance()
	{
		return Math.sqrt( Math.pow(ship.getXPos() - closestBody.getXPos(), 2) + Math.pow(ship.getYPos() - closestBody.getYPos(), 2) );
	}
	
	// gets the velocity of ship relative to the closest body
	private double getClosestBodyVel()
	{
		return Math.sqrt( Math.pow(ship.getXVel() - closestBody.getXVel(), 2) + Math.pow(ship.getYVel() - closestBody.getYVel(), 2) );
	}
	
	// check which planet is the closest to the spaceship
	public void setClosestBody()
	{
		if(closestBody == null)
			closestBody = planetarySystem.get(0);
		for(int i = 0; i < planetarySystem.size(); i++)
			if(Math.pow(planetarySystem.get(i).getXPos() - ship.getXPos(), 2) + Math.pow(planetarySystem.get(i).getYPos() - ship.getYPos(), 2) < Math.pow(closestBody.getXPos() - ship.getXPos(), 2) + Math.pow(closestBody.getYPos() - ship.getYPos(), 2))
				closestBody = planetarySystem.get(i);
	}
	
	// enabling restart and end-game buttons
	public void enableGameOverButtons(boolean enable)
	{
		frame.getRightPanel().getRestartButton().setEnabled(enable);
		frame.getRightPanel().getEndButton().setEnabled(enable);
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
	
	public MainFrame getMainFrame() { return frame; }
	
	// Sets
	public void setDT(int newDT) { dt = newDT; }
	
	public void setObjectNumber(int N) { objectNumber = N; }
	
	public void setTimeLeft(int newTimeLeft) { timeLeft = newTimeLeft; }
	
	public void setCurrentSize(Dimension size) { this.size = size; }
	
	public void setScale(long scale) { this.scale = scale; }
	
	public void setGameOver(boolean newGameOver) { gameOver = newGameOver; }
	
	public void setMainFrame(MainFrame frame) { if(this.frame == null) this.frame = frame; }
	
	private Background background;
	private Dimension size;
	//private final long initScale = 50000000L; 
	private long scale = 50000000L;
	//private long scale = 50L;
	
	private Color backgroundColor = Color.BLACK;
	private Spaceship ship;
	private List<CelestialBody> planetarySystem = new ArrayList<CelestialBody>(); // star and planets
	private int objectNumber; // number of celestial bodies in planetarySystem (planets + star)
	private int dt = HOUR * 2 / 60; // DT in seconds!!! -- one sec is one month
	public final int initDT = dt;
	private int timeLeft = 6000; // Only 700 sec?! Maybe will change to more
	private boolean gameOver = false, success = false;
	private CelestialBody closestBody = null;
	private final double initDistnace = 3.441e10, halfDistance = initDistnace / 2, thirdDistance = initDistnace / 3, fourthDistance = initDistnace / 4, fifthDistance = initDistnace / 5;
	private CurrDist rocketDist = CurrDist.ten;
	
	private MainFrame frame = null;
	// Global constants
	public static final int SECOND = 1, MINUTE = 60, HOUR = 3600, DAY = 24 * HOUR, MONTH = 30 * DAY, YEAR = 365 * DAY;
	
	// how close to the closest body is currently the ship
	// where each numeral means a fraction of initDistance
	// ten means >= initDistance
	private enum CurrDist
	{
		one, // 1/10 of initDist
		two, // 2/10 of initDist
		three, // 3/10 of initDist
		four,
		five,
		six,
		seven,
		eight,
		nine,
		ten
	}
}
