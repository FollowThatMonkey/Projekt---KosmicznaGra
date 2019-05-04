package windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objects.CelestialBody;

//Z
public class ChooseParametersPanel extends JPanel
{

	public ChooseParametersPanel(game.GameLogic logic)
	{
		setLayout(new GridLayout(8,1));
		JLabel shipNameLabel = new JLabel(parametersBundle.getString("setShipName"));
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
		
		JLabel shipTypeLabel = new JLabel(parametersBundle.getString("chooseShipType"));
		add(shipTypeLabel);
		//JList
		String ships[] = parametersBundle.getString("ships").split(","); //possible options
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
		
		JLabel planetarySystemLabel = new JLabel(parametersBundle.getString("chooseSystem"));
		add(planetarySystemLabel);
		//JList
		String systems[] = parametersBundle.getString("systems").split(","); //possible options
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
		
		// timeLimit
		JLabel timeLimitLabel = new JLabel(parametersBundle.getString("timeLimitLabel"));
		add(timeLimitLabel);
		timeLimitField = new JTextField("60");
		String timeUnits[] = {"s", "min."}; //possible options
		timeUnitsList = new JList<String>(timeUnits);
		timeUnitsList.setVisibleRowCount(1);
		timeUnitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // blocks selecting more than one item
		timeUnitsList.setSelectedIndex(0); // selects default item
		JPanel timeLimitPanel = new JPanel(new BorderLayout());
		timeLimitPanel.add(timeLimitField, BorderLayout.CENTER);
		timeLimitPanel.add(timeUnitsList, BorderLayout.LINE_END);
		
		unlimitedTime = new JCheckBox(parametersBundle.getString("unlimited"));
		unlimitedTime.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(unlimitedTime.isSelected())
				{
					logic.setTimeLeft(-1);
					timeLimitField.setEnabled(false);
					timeLimitField.setEditable(false);
				}
				else
				{
					timeLimitField.setEnabled(true);
					timeLimitField.setEditable(true);
				}
				
			}
		});
		timeLimitPanel.add(unlimitedTime, BorderLayout.LINE_START);
		add(timeLimitPanel);
		
	}
	
	public boolean setParameters(game.GameLogic logic)
	{	
		if (!unlimitedTime.isSelected())
		{
			try
			{
				setTimeLimit(logic);
				if (logic.getTimeLeft()<0)
					throw (new NegativeTimeException());
			}
			catch (NumberFormatException|NegativeTimeException e) 
			{
				timeLimitField.setText("60");
				JOptionPane.showMessageDialog(null,
						parametersBundle.getString("errorTimeLimit"),
					    parametersBundle.getString("errorTitle"),
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		//reading system parameters from file
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(systemOptions[systemOption]),
			        Charset.forName("UTF-8").newDecoder());
			BufferedReader br = new BufferedReader(isr);
			String temp = br.readLine();
			String tempSplited[];
			logic.setObjectNumber(Integer.parseInt(temp));
			
			for (int i=0; i<logic.getObjectNumber(); i++)
			{
				temp = br.readLine();
				String name = temp;
				temp = br.readLine();
				tempSplited = temp.split("\\s+");
				//setting parameters
				double mass = Double.parseDouble(tempSplited[0]);
				double xPosition = Double.parseDouble(tempSplited[1]);
				double yPosition = Double.parseDouble(tempSplited[2]);
				double xVelocity = Double.parseDouble(tempSplited[3]);
				double yVelocity = Double.parseDouble(tempSplited[4]);
				double radius = Double.parseDouble(tempSplited[5]);
				CelestialBody celestialBody = new CelestialBody(name, mass, xPosition, yPosition, xVelocity, yVelocity, radius);
				logic.getPlanetarySystem().add(celestialBody);
			}
			br.close();
			isr.close();
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
					parametersBundle.getString("errorClose"),
				    parametersBundle.getString("errorTitle"),
				    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		//reading ship parameters from file
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(shipOptions[shipOption]));
			String temp = br.readLine();
			String tempSplited[] = temp.split("\\s+");
			//setting parameters
			double mass = Double.parseDouble(tempSplited[0]);
			double xPosition = Double.parseDouble(tempSplited[1]);
			double yPosition = Double.parseDouble(tempSplited[2]);
			double xVelocity = Double.parseDouble(tempSplited[3]);
			double yVelocity = Double.parseDouble(tempSplited[4]);
			double dConsumption = Double.parseDouble(tempSplited[5]);
			logic.getShip().setParameters(mass, xPosition, yPosition, xVelocity, yVelocity, dConsumption);
			br.close();
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
					parametersBundle.getString("errorClose"),
				    parametersBundle.getString("errorTitle"),
				    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return true;
	}
	
	public void setTimeLimit(game.GameLogic logic)
	{
			int timeLimit = Integer.parseInt(timeLimitField.getText());
			if (timeUnitsList.getSelectedIndex() == 1)
				timeLimit*=logic.MINUTE;
			logic.setTimeLeft(timeLimit);
	}
	
	int systemOption = 0;
	int shipOption = 0;
	String systemOptions[] = {"system1.txt", "system2.txt", "system3.txt", "system4.txt"};
	String shipOptions[] = {"ship1.txt", "ship2.txt", "ship3.txt", "ship4.txt", "ship5.txt"};
	private ResourceBundle parametersBundle = ResourceBundle.getBundle("windows.ParametersBundle");
	JList<String> timeUnitsList;
	JTextField timeLimitField;
	JCheckBox unlimitedTime;
}
