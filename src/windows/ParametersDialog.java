package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Z
public class ParametersDialog extends JDialog
{

	public ParametersDialog(JFrame frame, game.GameLogic logic)
	{
		super(frame, "Wybierz parametry gry", true);
		setSize(400, 130);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //to stop users from closing the dialog before choosing parameters
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); //centering 
		
		JLabel questionLabel = new JLabel("W jaki sposób chcesz dokonać wyboru parametrów gry?", SwingConstants.CENTER);
		add(questionLabel, BorderLayout.PAGE_START);
		
		// button which closes the application
		JButton closeButton = new JButton("Wyjdź");
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		JPanel closeButtonPanel = new JPanel(new FlowLayout());
		closeButtonPanel.add(closeButton);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		//reading parameters from file
		JButton fileButton = new JButton("Wczytaj z pliku");
		fileButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				remove(questionLabel);
				remove(buttonPanel);
				revalidate();
				setTitle("Wczytaj parametry gry z pliku");
				JButton saveButton = new JButton("Zapisz");
				saveButton.setEnabled(false);
				saveButton.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						dispose();
					}
				});
				ReadParametersPanel readParametersPanel = new ReadParametersPanel(logic, saveButton);
				add(readParametersPanel, BorderLayout.CENTER);
				
				
				closeButtonPanel.add(saveButton, BorderLayout.PAGE_END);
				pack();
			}
		});
		buttonPanel.add(fileButton);
		
		//choosing parameters via menu
		JButton menuButton = new JButton("Wybierz za pomocą menu");
		menuButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				remove(questionLabel);
				remove(buttonPanel);
				revalidate();
				ChooseParametersPanel chooseParametersPanel = new ChooseParametersPanel(logic);
				add(chooseParametersPanel, BorderLayout.PAGE_START);
				
				JButton saveButton = new JButton("Zapisz");
				saveButton.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						try
						{
							chooseParametersPanel.setParameters(logic);
							dispose();
						}
						catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e1)
						{
							JOptionPane.showMessageDialog(null,
								    "Wystąpił błąd. Program zostanie zamknięty.",
								    "Wystąpił błąd",
								    JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
					}
				});
				closeButtonPanel.add(saveButton, BorderLayout.PAGE_END);
				pack();
			}
		});
		buttonPanel.add(menuButton);
		
		add(buttonPanel, BorderLayout.CENTER);
		add(closeButtonPanel, BorderLayout.PAGE_END);
		setVisible(true);
	}


}
