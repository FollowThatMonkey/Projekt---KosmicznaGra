package windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.CelestialBody;

public class ReadParametersPanel extends JPanel
{

	public ReadParametersPanel(game.GameLogic logic, JButton saveButton)
	{
		setSize(500, 200);
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		
		JButton instructionButton = new JButton(parametersBundle.getString("readInstruction"));
		instructionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null,
						parametersBundle.getString("instruction"),
						parametersBundle.getString("instructionTitle"),
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		this.add(instructionButton, BorderLayout.PAGE_START);
		JButton readButton = new JButton(parametersBundle.getString("chooseFile"));
		readButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle(parametersBundle.getString("chooseFile")); 
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
								setParameters(logic, isr);
								saveButton.setEnabled(true);
							}
							catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e1) 
							{
								JOptionPane.showMessageDialog(null,
										parametersBundle.getString("errorFile"),
										parametersBundle.getString("errorTitle"),
									    JOptionPane.ERROR_MESSAGE);
							}
							isr.close();
						} 
						catch (IOException e1)
						{
							JOptionPane.showMessageDialog(null,
									parametersBundle.getString("errorAgain"),
								    parametersBundle.getString("errorTitle"),
								    JOptionPane.ERROR_MESSAGE);
						}

				}
			}
		});
		add(readButton, BorderLayout.PAGE_END);
	}
	
	public void setParameters(game.GameLogic logic, InputStreamReader isr) throws IOException
	{
		BufferedReader br = new BufferedReader(isr);
		//ship parameters
		String temp = br.readLine();
		String name = temp;
		temp = br.readLine();
		String[] tempSplited = temp.split("\\s+");
		//setting parameters
		double mass = Double.parseDouble(tempSplited[0]);
		double xPosition = Double.parseDouble(tempSplited[1]);
		double yPosition = Double.parseDouble(tempSplited[2]);
		double xVelocity = Double.parseDouble(tempSplited[3]);
		double yVelocity = Double.parseDouble(tempSplited[4]);
		double dConsumption = Double.parseDouble(tempSplited[5]);
		logic.getShip().setParameters(name, mass, xPosition, yPosition, xVelocity, yVelocity, dConsumption);
		//system parameters
		temp = br.readLine();
		logic.setObjectNumber(Integer.parseInt(temp));
		
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
	
	private ResourceBundle parametersBundle = ResourceBundle.getBundle("windows.ParametersBundle");
}
