package windows;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.Spaceship;

// ZL

public class UpperPanel extends JPanel implements Runnable
{

	public UpperPanel(Spaceship spaceship) 
	{
		ship = spaceship;
		JLabel shipName = new JLabel("Nazwa statku: " + spaceship.getName(), SwingConstants.CENTER);
		//shows the spaceship's name
		shipVelocity = new JLabel(String.format("Aktualna prędkość: %.2f km/h", spaceship.speed()), SwingConstants.CENTER);
		//shows current spaceship's speed
		setLayout(new GridLayout(2, 1));
		add(shipName);
		add(shipVelocity);
		
		// Showing current velocity thread
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			shipVelocity.setText(String.format("Aktualna prędkość: %.2f km/h", ship.speed()));
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
	
	private JLabel shipVelocity;
	private Spaceship ship;
	
	// Sleep time in ms
	final int SLEEP_TIME = 500;
}
