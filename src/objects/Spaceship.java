package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GameLogic;
import objects.CosmicObjects.ObjectType;

// ZL

public class Spaceship extends CosmicObjects 
{

	public Spaceship(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double dC) 
	{
		super(nn, mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
		setType(ObjectType.spaceship);
		try
		{
			onRocket = ImageIO.read(new File("./RakietaOn.png"));
			offRocket = ImageIO.read(new File("./RakietaOff.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		resize(rocketWidth, rocketHight);
	}
	
	private void resize(int newWidth, int newHeight)
	{
		Image tmp = onRocket.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		onRocket = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = onRocket.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		
		tmp = offRocket.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		offRocket = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		g = offRocket.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		g.dispose();
	}
	
	public void update()
	{

		if(accelerate)
		{
			// movement available only if fuel > 0
			if(fuel > 0)
			{
				fuel -= dConsumption;
				setXVel(getXVel() + Math.cos(theta) * engineThrust);
				setYVel(getYVel() - Math.sin(theta) * engineThrust);
				setMass(getMass() - dConsumption * 10000);
			}
			if(fuel < 0)
				fuel = 0;
		}
		
		if(turnLeft)
		{
			theta += Math.PI / 60;
		}

		if(turnRight)
		{
			theta -= Math.PI / 60;
		}
	}
	
	// draw spaceship to buffImage
	public void draw(Graphics2D g2d, GameLogic logic)
	{
		/*
		BufferedImage tempImage = new BufferedImage((int)logic.getCurrentSize().getWidth(), (int)logic.getCurrentSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tempImage.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect((int)logic.getCurrentSize().getWidth() / 2 - 7, (int)logic.getCurrentSize().getHeight() / 2 - 20, 15, 40);
		g.setColor(Color.RED);
		g.fillOval((int)logic.getCurrentSize().getWidth() / 2 - 4, (int)logic.getCurrentSize().getHeight() / 2 - 20, 8, 8);
		g.rotate(- (theta - Math.PI / 2), (int)logic.getCurrentSize().getWidth() / 2, (int)logic.getCurrentSize().getHeight() / 2);
		
		g2d.drawImage(tempImage, g.getTransform(), null);
		*/
		if(accelerate && fuel > 0)
		{
			Graphics2D g = onRocket.createGraphics();
			g.rotate(- (theta - Math.PI / 2), onRocket.getWidth() / 2, onRocket.getHeight() / 2);
			g2d.drawImage(onRocket, new AffineTransformOp(g.getTransform(), AffineTransformOp.TYPE_BICUBIC), (int)logic.getCurrentSize().getWidth() / 2 + rocketWidth / 2, (int)logic.getCurrentSize().getHeight() / 2 - rocketHight / 2);
		}
		else
		{
			Graphics2D g = offRocket.createGraphics();
			g.rotate(- (theta - Math.PI / 2), offRocket.getWidth() / 2, offRocket.getHeight() / 2);
			g2d.drawImage(offRocket, new AffineTransformOp(g.getTransform(), AffineTransformOp.TYPE_BICUBIC), (int)logic.getCurrentSize().getWidth() / 2 + rocketWidth / 2, (int)logic.getCurrentSize().getHeight() / 2 - rocketHight / 2);
		}
	}
	
	//Movement functions
	public void moveUp(boolean b)
	{
		accelerate = b;
	}
	
	public void rotateLeft(boolean b)
	{
		turnLeft = b;	
	}
	
	public void rorateRight(boolean b)
	{
		turnRight = b;
	}

	//calculates spaceship's speed at the current moment
	public double speed() 
	{
		return Math.sqrt(Math.pow(getXVel(), 2) + Math.pow(getYVel(), 2)) / 1000;
	}
	
	// Gets
	public double getFuel() { return fuel; }
	
	public void setDConsumption(double dC) { dConsumption = dC; }
	
	public double getTheta() { return theta; }
	
	// Sets
	public void setParameters(String nn, double mm, double xPos, double yPos, double xVel, double yVel, double dC) 
	{
		super.setParameters(nn, mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
	}
	
	public void setParameters(double mm, double xPos, double yPos, double xVel, double yVel, double dC)
	{
		super.setParameters(mm, xPos, yPos, xVel, yVel);
		dConsumption = dC;
	}
	
	
	private final int rocketWidth = 30, rocketHight = 80;
	private double engineThrust = 1000 / 2;
	// Deegrees to X axis
	private double theta = Math.PI / 2;
	private double fuel = 100; // Fuel status in %%
	private double dConsumption;
	private boolean turnLeft = false, turnRight = false, accelerate = false;
	private BufferedImage offRocket, onRocket;
}
