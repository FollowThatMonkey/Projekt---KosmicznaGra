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

		String instruction = "<html>Plik powinien mieć następującą budowę:"
				+ "<br>W pierwszej linii powinna znajdować się nazwa statku kosmicznego, typu String.<br>"
				+ "W drugiej linii kolejno, oddzielone spacjami, liczby typu double:<br>"
				+ "masa pozycja_x pozycja_y prędkość_x prędkość_y zużycie_paliwa<br>"
				+ "W trzeciej linii powinna znadować się liczba N ciał niebieskich w układzie planetarnym, typu int.<br>"
				+ "W kolejnych N blokach po dwie linie powinny znajdować się:<br>"
				+ "w pierwszej linii nazwa ciała niebieskiego, typu String,<br>"
				+ "w drugiej linii, oddzielone spacjami, liczby typu double:<br>"
				+ "masa pozycja_x pozycja_y prędkość_x prędkość_y.<br>"
				+ "Jak pierwsza powinna być podana gwiazda centralna układu."
				+ "<br><br>Przykładowy plik może wygladać następująco:<br>"
				+ "Falcon Millenium<br>"
				+ "1420788	1.84e11	0	0	0	0.0008<br>"
				+ "2<br>"
				+ "Słońce<br>"
				+ "1.98855e30	0	0	0	0<br>"
				+ "Ziemia<br>"
				+ "5.97219e24	1.4959e11	0	0	29.78e3<br></html>";
		
		JButton instructionButton = new JButton("Zobacz instrukcję");
		instructionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null,
					    instruction,
					    "Instrukcja",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		this.add(instructionButton, BorderLayout.PAGE_START);
		JButton readButton = new JButton("Wybierz plik");
		readButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Wybierz plik do otwarcia"); 
				fileChooser.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
				int userSelection = fileChooser.showOpenDialog(null);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) 
				{
						InputStreamReader isr;
						try
						{
							isr = new InputStreamReader(new FileInputStream(fileChooser.getSelectedFile()),
							        Charset.forName("UTF-8").newDecoder());
							setParameters(logic, isr);
							saveButton.setEnabled(true);
							isr.close();
						} catch (FileNotFoundException e1)
						{
							e1.printStackTrace();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
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
			CelestialBody celestialBody = new CelestialBody(name, mass, xPosition, yPosition, xVelocity, yVelocity);
			logic.getPlanetarySystem().add(celestialBody);
		}
		br.close();
	}
	

}
