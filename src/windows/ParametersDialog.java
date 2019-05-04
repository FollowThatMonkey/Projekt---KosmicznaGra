package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

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
		super(frame, true);
		setName("parametersDialog");
		setTitle(parametersBundle.getString("chooseTitle"));
		setSize(400, 130);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //to stop users from closing the dialog before choosing parameters
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); //centering 
		
		add(questionLabel, BorderLayout.PAGE_START);
		
		// button which closes the application
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		saveAndClosePanel.add(closeButton);
		add(saveAndClosePanel, BorderLayout.PAGE_END);
		
		class SaveButtonListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand() == "read")
				{
					dispose();
				}
				if(e.getActionCommand() == "choose")
				{
					try
					{
						if (chooseParametersPanel.setParameters(logic))
							dispose();
					}
					catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e1)
					{
						JOptionPane.showMessageDialog(null,
								parametersBundle.getString("errorClose"),
							    parametersBundle.getString("errorTitle"),
							    JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
				}
				
			}
			
		}
		
		class ParametersButtonListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				remove(questionLabel);
				remove(buttonPanel);
				remove(saveAndClosePanel);
				revalidate();
				
				if (e.getActionCommand()=="read")
				{
					setTitle(parametersBundle.getString("readTitle"));

					saveButton.setActionCommand("read");
					saveButton.setEnabled(false);
					saveButton.addActionListener(new SaveButtonListener());
					saveAndClosePanel.add(saveButton);
					readParametersPanel = new ReadParametersPanel(logic, saveButton);
					add(readParametersPanel, BorderLayout.PAGE_START);
					add(saveAndClosePanel, BorderLayout.PAGE_END);
				}
				if (e.getActionCommand() == "choose")
				{
					chooseParametersPanel = new ChooseParametersPanel(logic);
					add(chooseParametersPanel, BorderLayout.PAGE_START);
					
					saveButton.setActionCommand("choose");
					saveButton.addActionListener(new SaveButtonListener());
					saveAndClosePanel.add(saveButton);
					add(saveAndClosePanel, BorderLayout.PAGE_END);
					
				}
				pack();
			}
		}

		//reading parameters from file
		fileButton.setActionCommand("read");
		fileButton.addActionListener(new ParametersButtonListener());
		buttonPanel.add(fileButton);
		
		//choosing parameters via menu
		menuButton.setActionCommand("choose");
		menuButton.addActionListener(new ParametersButtonListener());
		buttonPanel.add(menuButton);
		
		add(buttonPanel, BorderLayout.CENTER);
		setVisible(true);
	}


	private ResourceBundle parametersBundle = ResourceBundle.getBundle("windows.ParametersBundle");
	JLabel questionLabel = new JLabel(parametersBundle.getString("question"), SwingConstants.CENTER);
	JButton closeButton = new JButton(parametersBundle.getString("close"));
	JButton saveButton = new JButton(parametersBundle.getString("save"));
	JPanel saveAndClosePanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JButton fileButton = new JButton(parametersBundle.getString("read"));
	JButton menuButton = new JButton(parametersBundle.getString("chooseMenu"));
	ChooseParametersPanel chooseParametersPanel;
	ReadParametersPanel readParametersPanel;
}
