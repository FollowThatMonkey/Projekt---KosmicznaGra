package windows;

import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.GameLogic;
import objects.Spaceship;

// ZL

public class UpperPanel extends JPanel implements Runnable
{

	public UpperPanel(GameLogic logic) 
	{
		this.logic = logic;
		shipName = new JLabel(windowBundle.getString("shipNameString")+ " " + logic.getShip().getName(), SwingConstants.CENTER);
		//shows the spaceship's name
		shipVelocity = new JLabel(String.format(windowBundle.getString("shipSpeed") + " %.2f km/s", logic.getShip().speed()), SwingConstants.CENTER);
		//shows current spaceship's speed
		setLayout(new GridLayout(2, 1));
		add(shipName);
		add(shipVelocity);
		
		// Showing current velocity thread
		if(thread == null)
		{
			thread = new Thread(this);
			thread.start();	
		}
	}
	
	@Override
	public void run()
	{
		while(!logic.getGameOver())
		{
			shipVelocity.setText(String.format(windowBundle.getString("shipSpeed") + " %.2f km/s", logic.getShip().speed()));
			try
			{
				Thread.sleep(SLEEP_TIME);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// Gets
	public JLabel getShipNameLabel() { return shipName; }
	
	public Thread getThread() { return thread; }
	
	public ResourceBundle getWindowBundle() { return windowBundle; }
	
	// Sets
	public void setLogic(GameLogic logic) { this.logic = logic; }
	
	public void setThread(Thread thread) { this.thread = thread; }
	
	private ResourceBundle windowBundle = ResourceBundle.getBundle("bundles/WindowBundle");
	private JLabel shipVelocity;
	private GameLogic logic;
	private JLabel shipName;
	private Thread thread = null;
	// Sleep time in ms
	final int SLEEP_TIME = 500;
}
