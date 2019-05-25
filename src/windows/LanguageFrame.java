package windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import game.GameLogic;

public class LanguageFrame extends JFrame
{

	public LanguageFrame(GameLogic logic) throws HeadlessException
	{
		setTitle("Choose your language");
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLayout(new BorderLayout());
		String languages[] = {"English(GB)", "polski"}; //possible options
		Locale supportedLocales[] = {new Locale("en", "GB"), new Locale("pl", "PL")};
		Locale.setDefault(supportedLocales[languagesOption]);
		
		//list of available languages
		JList<String> languagesList = new JList<String>(languages);
		languagesList.setVisibleRowCount(3);
		languagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // blocks selecting more than one item
		languagesList.setSelectedIndex(0); // selects default item
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
				languagesOption = languagesList.getSelectedIndex();
				Locale.setDefault(supportedLocales[languagesOption]);
				dispose();
				ParametersFrame parametersFrame = new ParametersFrame(logic);
			}
		});
		buttonPanel.add(saveButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		setSize(350, 130);
		setLocationRelativeTo(null); //centering 
		setVisible(true);
	}

	
	int languagesOption = 0;


}
