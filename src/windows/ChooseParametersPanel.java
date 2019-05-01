package windows;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objects.CelestialBody;

//Z
public class ChooseParametersPanel extends JPanel
{

	public ChooseParametersPanel(game.GameLogic logic)
	{
		setLayout(new GridLayout(6,1));
		
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
		String ships[] = {"Ship A", "Ship B", "Ship C", "Ship D", "Ship E"}; //possible options
		JList<String> shipList = new JList<String>(ships);
		shipList.setVisibleRowCount(3);
		shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // blocks selecting more than one item
		shipList.setSelectedIndex(0); // selects default item
		shipList.addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				shipOption = shipList.getSelectedIndex();
			}
		});
		JScrollPane shipsListScrollPane = new JScrollPane(shipList);
		add(shipsListScrollPane);
		
		JLabel planetarySystemLabel = new JLabel("Wybierz układ planetarny: ");
		add(planetarySystemLabel);
		//JList
		String systems[] = {"Solar System", "A System", "B System", "C System"}; //possible options
		JList<String> systemList = new JList<String>(systems);
		systemList.setVisibleRowCount(3);
		systemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // blocks selecting more than one item
		systemList.setSelectedIndex(0); // selects default item
		systemList.addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				systemOption = systemList.getSelectedIndex();
			}
		});
		JScrollPane systemListScrollPane = new JScrollPane(systemList);
		add(systemListScrollPane);
	}
	
	public void setParameters(game.GameLogic logic)
	{
		//reading system parameters from file
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(systemOptions[systemOption]));
			String temp = br.readLine();
			String tempSplited[];
			logic.setObjectNumber(Integer.parseInt(temp));
			
			for (int i=0; i<logic.getObjectNumber(); i++)
			{
				temp = br.readLine();
				tempSplited = temp.split("\\s+");
				double mass = Double.parseDouble(tempSplited[1]);
				double xPosition = Double.parseDouble(tempSplited[2]);
				double yPosition = Double.parseDouble(tempSplited[3]);
				double xVelocity = Double.parseDouble(tempSplited[4]);
				double yVelocity = Double.parseDouble(tempSplited[5]);
				CelestialBody celestialBody = new CelestialBody(tempSplited[0], mass, xPosition, yPosition, xVelocity, yVelocity);
				logic.getPlanetarySystem().add(celestialBody);
			}
			br.close();
		} 
		catch (IOException e)
		{
			System.out.println(e.toString());
			System.exit(1);
		}
		
		//reading ship parameters from file
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(shipOptions[shipOption]));
			String temp = br.readLine();
			String tempSplited[] = temp.split("\\s+");
			double mass = Double.parseDouble(tempSplited[0]);
			double xPosition = Double.parseDouble(tempSplited[1]);
			double yPosition = Double.parseDouble(tempSplited[2]);
			double xVelocity = Double.parseDouble(tempSplited[3]);
			double yVelocity = Double.parseDouble(tempSplited[4]);
			double dConsumption = Double.parseDouble(tempSplited[5]);
			logic.getShip().setMass(mass);
			logic.getShip().setXPos(xPosition);
			logic.getShip().setYPos(yPosition);
			logic.getShip().setXVel(xVelocity);
			logic.getShip().setYVel(yVelocity);
			logic.getShip().setDConsumption(dConsumption);
			br.close();
		} 
		catch (IOException e)
		{
			System.out.println(e.toString());
			System.exit(1);
		}
	}
	
	int systemOption = 0;
	int shipOption = 0;
	String systemOptions[] = {"system1.txt", "system2.txt", "system3.txt", "system4.txt"};
	String shipOptions[] = {"ship1.txt", "ship2.txt", "ship3.txt", "ship4.txt", "ship5.txt"};
}
