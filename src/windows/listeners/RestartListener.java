package windows.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.GameLogic;
import windows.MainFrame;

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
		
	}

	private GameLogic logic;
	private MainFrame frame;
}
