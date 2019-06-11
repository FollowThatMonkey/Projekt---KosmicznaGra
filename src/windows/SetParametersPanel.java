package windows;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.GameLogic;

//Z
public class SetParametersPanel extends JPanel
{

	public SetParametersPanel(ParametersFrame parametersFrame)
	{
		
		parametersFrame.setTitle(parametersFrame.parametersBundle.getString("chooseTitle"));
		setLayout(new BorderLayout());
		
		JLabel questionLabel = new JLabel(parametersFrame.parametersBundle.getString("question"), SwingConstants.CENTER);
		add(questionLabel, BorderLayout.PAGE_START);
		
		JPanel buttonPanel = new JPanel();
		JButton fileButton = new JButton(parametersFrame.parametersBundle.getString("read"));
		fileButton.setActionCommand("read");
		JButton menuButton = new JButton(parametersFrame.parametersBundle.getString("chooseMenu"));
		menuButton.setActionCommand("choose");

		//button listener which changes to a different card
		class ButtonListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				((CardLayout) parametersFrame.cardPanel.getLayout()).show(parametersFrame.cardPanel, e.getActionCommand());
				if(e.getActionCommand() == "read")
				{
					parametersFrame.setSize(350, 130);
					parametersFrame.setTitle(parametersFrame.parametersBundle.getString("readTitle"));
				}
				if(e.getActionCommand() == "choose")
					parametersFrame.setSize(350, 300);
			}
				
		}
		
		fileButton.addActionListener(new ButtonListener());
		menuButton.addActionListener(new ButtonListener());
		
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
		buttonPanel.add(fileButton, BorderLayout.LINE_START);
		buttonPanel.add(menuButton, BorderLayout.LINE_END);
		JPanel closeButtonPanel = new JPanel();
		closeButtonPanel.add(closeButton);
		buttonPanel.add(closeButtonPanel, BorderLayout.PAGE_END);
		add(buttonPanel, BorderLayout.CENTER);
		
	}


}
