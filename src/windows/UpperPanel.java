package windows;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.Spaceship;

public class UpperPanel extends JPanel 
{

	public UpperPanel(Spaceship spaceship) 
	{
		JLabel shipName = new JLabel("Nazwa statku: " + spaceship.getName(), SwingConstants.CENTER);
		//shows the spaceship's name
		JLabel shipVelocity = new JLabel(String.format("Aktualna prędkość: %.2f km/h", spaceship.speed()), SwingConstants.CENTER);
		//shows current spaceship's speed
		setLayout(new GridLayout(2, 1));
		add(shipName);
		add(shipVelocity);
	}
}
