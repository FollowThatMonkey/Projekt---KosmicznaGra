package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.GameLogic;

// RJ

public class GamePanel extends JPanel implements Runnable, KeyListener, ComponentListener
{

	public GamePanel(GameLogic logic) 
	{
		this.logic = logic;
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
		setSize(10, 10);
		
		// Starting game thread!
		if(thread == null)
		{
			thread = new Thread(this);
			addComponentListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	@Override
	public void run()
	{
		long start, elapsed, wait;

		running = true;
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
		
		logic.setCurrentSize(new Dimension(image.getWidth(), image.getHeight()));
		
		while(running)
		{
			start = System.nanoTime();
			//this.requestFocus();
			
			update(); // calculations and stuff
			draw(); // draw everything to buffImage
			drawToScreen(); // draw everything on panel
			
			elapsed = System.nanoTime() - start;
			
			wait = (long) (targetTime - elapsed / 10e6); // wait in milisec
			
			if(wait < 0)
				wait = 0;
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
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	
	public void resize()
	{
		logic.setCurrentSize(new Dimension(image.getWidth(), image.getHeight()));
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
		System.out.println("Changed windows size to " + logic.getCurrentSize());
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

	@Override
	public void componentResized(ComponentEvent e)
	{
		resize();
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
