package windows;

import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
//Z
import javax.swing.JPanel;

import game.GameLogic;

public class ParametersFrame extends JFrame
{

	//constructor
	public ParametersFrame(GameLogic logic) throws HeadlessException
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		
		cardPanel = new JPanel(new CardLayout());
		
		//card 1 - ReadParametersPanel
		ReadParametersPanel readParametersPanel = new ReadParametersPanel(this, logic);
		cardPanel.add(readParametersPanel, "read");
		//card 2 - SetParametersPanel
		SetParametersPanel setParametersPanel = new SetParametersPanel(this);
		cardPanel.add(setParametersPanel, "set");
		//card 3 - ChooseParametersPanel
		ChooseParametersPanel chooseParametersPanel = new ChooseParametersPanel(this, logic);
		cardPanel.add(chooseParametersPanel, "choose");
		
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, "set");
		setTitle(parametersBundle.getString("chooseTitle"));
		this.add(cardPanel);
		setSize(350, 130);
		setLocationRelativeTo(null); //centering 
		setVisible(true);
	}

	ResourceBundle parametersBundle = ResourceBundle.getBundle("bundles/ParametersBundle");
	JPanel cardPanel;
}
