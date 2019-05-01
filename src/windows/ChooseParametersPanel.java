package windows;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objects.CelestialBody;
import objects.Spaceship;

//Z - doesn't work yet
public class ChooseParametersPanel extends JPanel
{

	public ChooseParametersPanel(game.GameLogic logic)
	{
		setLayout(new GridLayout(5,1));
		
		JLabel shipNameLabel = new JLabel("Nazwa statku kosmicznego: ");
		add(shipNameLabel);
		
		JTextField shipNameField = new JTextField();
		KeyListener shipNameListener = new KeyAdapter()
		{
			
			public void keyReleased(KeyEvent e)
			{
				logic.getShip().setName(shipNameField.getText());
			}
		};
		shipNameField.addKeyListener(shipNameListener);
		add(shipNameField);
		
		JLabel shipTypeLabel = new JLabel("Wybierz model statku kosmicznego: ");
		add(shipTypeLabel);
		//JList
		
		JLabel planetarySystemLabel = new JLabel("Wybierz układ planetarny: ");
		add(planetarySystemLabel);
		//JList
		
	
	}
}
