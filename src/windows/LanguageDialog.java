package windows;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.GameLogic;

public class LanguageDialog extends JDialog
{

	public LanguageDialog(JFrame frame, GameLogic logic)
	{
		super(frame, true);
		setTitle("Choose your language");
		setSize(400, 130);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //to stop users from closing the dialog before choosing parameters
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); //centering 
		

		String languages[] = {"English(GB)", "polski"}; //possible options
		Locale supportedLocales[] = {new Locale("en", "GB"), new Locale("pl", "PL")};
		Locale.setDefault(supportedLocales[languagesOption]);
		
		//list of available languages
		JList<String> languagesList = new JList<String>(languages);
		languagesList.setVisibleRowCount(3);
		languagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // blocks selecting more than one item
		languagesList.setSelectedIndex(0); // selects default item
		languagesList.addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				languagesOption = languagesList.getSelectedIndex();
				Locale.setDefault(supportedLocales[languagesOption]);
			}
		});
		JScrollPane languagessListScrollPane = new JScrollPane(languagesList);
		add(languagessListScrollPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();

		
		// button which closes the application
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		buttonPanel.add(closeButton);
		
		//save button
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				// Dialog - choosing game parameters
				ParametersDialog parametersDialog = new ParametersDialog(frame, logic);
			}
		});
		buttonPanel.add(saveButton);
		
		add(buttonPanel, BorderLayout.PAGE_END);
		setVisible(true);
	}

	
	int languagesOption = 0;
}
