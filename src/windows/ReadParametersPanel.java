package windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import game.GameLogic;
import objects.CelestialBody;
//Z 
public class ReadParametersPanel extends JPanel
{
	//constructor
	public ReadParametersPanel(ParametersFrame parametersFrame, GameLogic logic)
	{
		this.logic = logic;
		//buttons: go back, close, save
		JPanel basicButtonsPanel = new JPanel();
				
		//go back button
		JButton goBackButton = new JButton(parametersFrame.parametersBundle.getString("goBack"));
		goBackButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				((CardLayout) parametersFrame.cardPanel.getLayout()).show(parametersFrame.cardPanel, "set");
				parametersFrame.setSize(350, 130);
				parametersFrame.setTitle(parametersFrame.parametersBundle.getString("chooseTitle"));
			}
		});
		basicButtonsPanel.add(goBackButton);		
		
		//close button
		JButton closeButton = new JButton(parametersFrame.parametersBundle.getString("close"));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		basicButtonsPanel.add(closeButton);
				
		//save button
		JButton saveButton = new JButton(parametersFrame.parametersBundle.getString("save"));
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parametersFrame.dispose();
				if (logic.getGameOver()) //restart game
				{
					logic.setGameOver(false);
				}
				else //start game
				{
					MainFrame frame = new MainFrame(logic);
				}
			}
		});
		basicButtonsPanel.add(saveButton);
		
		JPanel readButtonsPanel = new JPanel();
		//button - read instruction
		JButton instructionButton = new JButton(parametersFrame.parametersBundle.getString("readInstruction"));
		instructionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null,
						parametersFrame.parametersBundle.getString("instruction"),
						parametersFrame.parametersBundle.getString("instructionTitle"),
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		readButtonsPanel.add(instructionButton);
		
		//button - choose file to open
		JButton readButton = new JButton(parametersFrame.parametersBundle.getString("chooseFile"));
		readButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle(parametersFrame.parametersBundle.getString("chooseFile")); 
				fileChooser.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
				int userSelection = fileChooser.showOpenDialog(null);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) 
				{
						InputStreamReader isr;
						try
						{
							isr = new InputStreamReader(new FileInputStream(fileChooser.getSelectedFile()),
							        Charset.forName("UTF-8").newDecoder());
							try
							{
								setParameters(isr);
								saveButton.setEnabled(true);
							}
							catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e1) 
							{
								//invalid file
								JOptionPane.showMessageDialog(null,
										parametersFrame.parametersBundle.getString("errorFile"),
										parametersFrame.parametersBundle.getString("errorTitle"),
									    JOptionPane.ERROR_MESSAGE);
							}
							isr.close();
						} 
						catch (IOException e1)
						{
							JOptionPane.showMessageDialog(null,
									parametersFrame.parametersBundle.getString("errorAgain"),
									parametersFrame.parametersBundle.getString("errorTitle"),
								    JOptionPane.ERROR_MESSAGE);
						}

				}
			}
		});
		readButtonsPanel.add(readButton);
		
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(readButtonsPanel, BorderLayout.CENTER);
		buttonsPanel.add(basicButtonsPanel, BorderLayout.PAGE_END);
		add(buttonsPanel);
	}
	
	// function which reads from file and sets parameters; 
	//exception handling in constructor, read button action listener
	public void setParameters(InputStreamReader isr) throws IOException
	{
		BufferedReader br = new BufferedReader(isr);
		//ship parameters
		String temp = br.readLine();
		String name = temp;
		temp = br.readLine();
		String[] tempSplited = temp.split("\\s+");
		//setting ship parameters
		double mass = Double.parseDouble(tempSplited[0]);
		double xPosition = Double.parseDouble(tempSplited[1]);
		double yPosition = Double.parseDouble(tempSplited[2]);
		double xVelocity = Double.parseDouble(tempSplited[3]);
		double yVelocity = Double.parseDouble(tempSplited[4]);
		double dConsumption = Double.parseDouble(tempSplited[5]);
		int timeLeft = Integer.parseInt(tempSplited[6]);
		logic.getShip().setParameters(name, mass, xPosition, yPosition, xVelocity, yVelocity, dConsumption);
		logic.setTimeLeft(timeLeft);
		//system parameters
		temp = br.readLine();
		logic.setObjectNumber(Integer.parseInt(temp));
		//setting ship parameters
		for (int i=0; i<logic.getObjectNumber(); i++)
		{
			temp = br.readLine();
			name = temp;
			temp = br.readLine();
			tempSplited = temp.split("\\s+");
			//setting parameters
			mass = Double.parseDouble(tempSplited[0]);
			xPosition = Double.parseDouble(tempSplited[1]);
			yPosition = Double.parseDouble(tempSplited[2]);
			xVelocity = Double.parseDouble(tempSplited[3]);
			yVelocity = Double.parseDouble(tempSplited[4]);
			double radius = Double.parseDouble(tempSplited[5]);
			CelestialBody celestialBody = new CelestialBody(name, mass, xPosition, yPosition, xVelocity, yVelocity, radius);
			logic.getPlanetarySystem().add(celestialBody);
		}
		br.close();
	}
	
	private GameLogic logic;
}
