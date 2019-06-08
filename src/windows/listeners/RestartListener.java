package windows.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.GameLogic;
import windows.ParametersFrame;

public class RestartListener implements ActionListener
{
	public RestartListener(GameLogic logic)
	{
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		logic.setGameOver(true);
		// opening parametersFrame and creating new GameLogic with linked MainFrame
		ParametersFrame parametersFrame = new ParametersFrame(new GameLogic(logic.getMainFrame()));
	}
	
	GameLogic logic;
}