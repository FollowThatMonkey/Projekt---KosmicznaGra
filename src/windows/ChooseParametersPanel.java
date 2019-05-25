package windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.GameLogic;
import objects.CelestialBody;

//Z
public class ChooseParametersPanel extends JPanel
{
	//constructor
	public ChooseParametersPanel(ParametersFrame parametersFrame, GameLogic logic)
	{
		this.logic = logic;
		setLayout(new BorderLayout());
		JPanel choosePanel  = new JPanel();
		choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));
		
		//ship name block /start/
		JPanel shipNamePanel = new JPanel(new BorderLayout());
		JLabel shipNameLabel = new JLabel(parametersBundle.getString("setShipName"));
		shipNamePanel.add(shipNameLabel, BorderLayout.PAGE_START);
		
		JTextField shipNameField = new JTextField();
		KeyListener shipNameListener = new KeyAdapter()
		{
			
			public void keyReleased(KeyEvent e)
			{
				logic.getShip().setName(shipNameField.getText());
			}
		};
		shipNameField.addKeyListener(shipNameListener);
		shipNamePanel.add(shipNameField, BorderLayout.PAGE_END);
		choosePanel.add(shipNamePanel);
		//ship name block /end/
		
		//ship type block /start/
		JPanel shipTypePanel = new JPanel(new BorderLayout());
		JLabel shipTypeLabel = new JLabel(parametersBundle.getString("chooseShipType"));
		shipTypePanel.add(shipTypeLabel, BorderLayout.PAGE_START);
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
		shipTypePanel.add(shipsListScrollPane, BorderLayout.CENTER);
		choosePanel.add(shipTypePanel);
		//ship type block /end/
		
		//planetary system block /start/
		JPanel planetarySystemPanel = new JPanel(new BorderLayout());
		JLabel planetarySystemLabel = new JLabel(parametersBundle.getString("chooseSystem"));
		planetarySystemPanel.add(planetarySystemLabel, BorderLayout.PAGE_START);
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
		planetarySystemPanel.add(systemListScrollPane, BorderLayout.CENTER);
		choosePanel.add(planetarySystemPanel);
		//planetary system block /end/
		
		//time limit block /start/
		JPanel timeLimitPanel = new JPanel(new BorderLayout());
		JLabel timeLimitLabel = new JLabel(parametersBundle.getString("timeLimitLabel"));
		timeLimitPanel.add(timeLimitLabel, BorderLayout.PAGE_START);
		timeLimitField = new JTextField("60"); //initial value
		String timeUnits[] = {"s", "min."}; //possible units
		//JComboBox
		timeUnitsList = new JComboBox<String>(timeUnits);
		timeUnitsList.setSelectedIndex(0); // selects default item
		
		JPanel timeFieldPanel = new JPanel(new BorderLayout());
		timeFieldPanel.add(timeLimitField, BorderLayout.PAGE_START);
		timeLimitPanel.add(timeFieldPanel, BorderLayout.CENTER);
		
		JPanel timeUnitsPanel = new JPanel(new BorderLayout());
		timeUnitsPanel.add(timeUnitsList, BorderLayout.PAGE_START);
		timeLimitPanel.add(timeUnitsPanel, BorderLayout.LINE_END);
		
		//unlimited time - check box
		unlimitedTime = new JCheckBox(parametersBundle.getString("unlimited"));
		unlimitedTime.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(unlimitedTime.isSelected())
				{
					logic.setTimeLeft(-1);
					//disabling option to choose a time limit
					timeLimitField.setEnabled(false);
					timeLimitField.setEditable(false);
					timeUnitsList.setEnabled(false);
				}
				else
				{
					//enabling option to choose a time limit
					timeLimitField.setEnabled(true);
					timeLimitField.setEditable(true);
					timeUnitsList.setEnabled(true);
				}
				
			}
		});
		timeLimitPanel.add(unlimitedTime, BorderLayout.PAGE_END);
		choosePanel.add(timeLimitPanel);
		//time limit block /end/
		
		
		//buttons
		JPanel buttonsPanel = new JPanel();
		
		//go back button
		JButton goBackButton = new JButton(parametersBundle.getString("goBack"));
		goBackButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				((CardLayout) parametersFrame.cardPanel.getLayout()).show(parametersFrame.cardPanel, "set");
				parametersFrame.setSize(350, 130);
			}
		});
		buttonsPanel.add(goBackButton);
		
		//close button
		JButton closeButton = new JButton(parametersBundle.getString("close"));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		buttonsPanel.add(closeButton);
		
		//save button
		JButton saveButton = new JButton(parametersBundle.getString("save"));
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if (setParameters()) //if managed to set parameters
					{
						parametersFrame.dispose();
						MainFrame frame = new MainFrame(logic);
					}
				}
				catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e1)
				{
					//fatal error, exit
					JOptionPane.showMessageDialog(null,
							parametersBundle.getString("errorClose"),
						    parametersBundle.getString("errorTitle"),
						    JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		});
		buttonsPanel.add(saveButton);
		
		
		add(choosePanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}
	
	public boolean setParameters()
	{	
		//setting time limit (timeLeft value)
		if (!unlimitedTime.isSelected())
		{
			try
			{
				setTimeLimit();
				if (logic.getTimeLeft()<=0)
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
		//setting system parameters
		setSystemParameters();
		//setting ship parameters
		setShipParamters();
		
		return true;
	}
	
	public void setTimeLimit()
	{
			int timeLimit = Integer.parseInt(timeLimitField.getText());
			if (timeUnitsList.getSelectedIndex() == 1)
				timeLimit*=logic.MINUTE;
			logic.setTimeLeft(timeLimit);
	}
	
	public void setSystemParameters()
	{	
		//reading system parameters from file
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(systemOptions[systemOption]),
			        Charset.forName("UTF-8").newDecoder());
			BufferedReader br = new BufferedReader(isr);
			String temp = br.readLine();
			String tempSplited[];
			logic.setObjectNumber(Integer.parseInt(temp));
			
			//setting parameters
			//exceptions handled in ParametersDialog
			for (int i=0; i<logic.getObjectNumber(); i++)
			{
				temp = br.readLine();
				tempSplited = temp.split("\\s+");
				//setting parameters
				String name = parametersBundle.getString("system"+(systemOption+1)).split(",")[i];
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
		
	}
	
	public void setShipParamters()
	{
		//reading ship parameters from file
				try
				{
					BufferedReader br = new BufferedReader(new FileReader(shipOptions[shipOption]));
					String temp = br.readLine();
					String tempSplited[] = temp.split("\\s+");
					//setting parameters
					//exceptions handled in ParametersDialog
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
	}
	
	private GameLogic logic;
	private int systemOption = 0;
	private int shipOption = 0;
	private String systemOptions[] = {"system1.txt", "system2.txt", "system3.txt", "system4.txt"};
	private String shipOptions[] = {"ship1.txt", "ship2.txt", "ship3.txt", "ship4.txt", "ship5.txt"};
	private ResourceBundle parametersBundle = ResourceBundle.getBundle("windows.ParametersBundle");
	private JComboBox<String> timeUnitsList;
	private JTextField timeLimitField;
	private JCheckBox unlimitedTime;
}
