package windows.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import game.GameLogic;
import windows.MainFrame;
import windows.ParametersFrame;

public class RestartListener implements ActionListener
{
	public RestartListener(GameLogic logic, MainFrame frame)
	{
		this.logic = logic;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		ParametersFrame parametersFrame = new ParametersFrame(logic);
		//frame.getRightPanel().getRestartButton().setEnabled(false);//doesn't work????
		//((JButton) e.getSource()).setEnabled(false); //doesn't work either
	}

	private GameLogic logic;
	private MainFrame frame;
}
