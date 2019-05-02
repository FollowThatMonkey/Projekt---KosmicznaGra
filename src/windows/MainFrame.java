package windows;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.GameLogic;

// Z

public class MainFrame extends JFrame 
{
	
	public MainFrame(GameLogic logic) 
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setMinimumSize(getSize());
		setLocationRelativeTo(null); //centering 
		setTitle("Kosmiczna gra");
		setLayout(new BorderLayout());
		
		// Dialog - choosing game parameters
		ParametersDialog parametersDialog = new ParametersDialog(this, logic);
		
		// Here to add Panels!
		upperPanel = new UpperPanel(logic.getShip());
		rightPanel = new RightPanel(logic, this);
		gamePanel = new GamePanel(logic);
		
		add(upperPanel, BorderLayout.PAGE_START);
		add(rightPanel, BorderLayout.LINE_END);
		add(gamePanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	UpperPanel upperPanel;
	RightPanel rightPanel;
	GamePanel gamePanel;
	
	public static void main(String[] args) 
	{
		GameLogic logic = new GameLogic();
		SwingUtilities.invokeLater(new Runnable() 
		{

			public void run() 
			{
				MainFrame frame = new MainFrame(logic);
			}
		});
	}
}
