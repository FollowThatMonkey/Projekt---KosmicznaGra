package windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.GameLogic;

// RJ

public class GamePanel extends JPanel implements Runnable, KeyListener
{

	public GamePanel(GameLogic logic) 
	{
		this.logic = logic;
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
		
		logic.setThreads();
		
		// Starting game thread!
		running = true;
		if(thread == null)
		{
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	@Override
	public void run()
	{
		long start, elapsed, wait;
		
		while(running)
		{
			start = System.nanoTime();
			
			update(); // calculations and stuff
			draw(); // draw everything to buffImage
			drawToScreen(); // draw everything on panel
			
			elapsed = System.nanoTime() - start;
			
			wait = (long) (targetTime - elapsed / 10e6); // wait in milisec
			
			try
			{
				Thread.sleep(wait);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	private void update()
	{
		logic.update();
	}
	
	private void draw()
	{
		logic.draw(g2d);
	}
	
	private void drawToScreen()
	{
		// Add drawing to screen
	}

	@Override
	public void keyTyped(KeyEvent key)
	{
	}

	@Override
	public void keyPressed(KeyEvent key)
	{
		logic.keyPressed(key.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent key)
	{
		logic.keyReleased(key.getKeyCode());
	}
	
	// Game Thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g2d;
	
	// GameLogic??
	private GameLogic logic;
}
