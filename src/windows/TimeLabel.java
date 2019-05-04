package windows;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import game.GameLogic;

public class TimeLabel extends JLabel implements Runnable
{
	public TimeLabel(GameLogic logic, int boldTextSize)
	{
		super(logic.getTimeLeft() + "s", SwingConstants.CENTER);
		this.logic = logic;

		setFont(new Font(getFont().getFontName(), Font.BOLD, boldTextSize));
		setPreferredSize(new Dimension(150, 75));
		setVerticalTextPosition(JLabel.TOP);
		setVerticalAlignment(JLabel.TOP);
		
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		if(logic.getTimeLeft() >= 0)
		{
			while(logic.getTimeLeft() > 0)
			{
				setText(logic.getTimeLeft() + "s");
				try
				{
					// Sleep for 1sec
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				logic.setTimeLeft(logic.getTimeLeft() - 1);
			}
			setText(logic.getTimeLeft() + "s");
		}
		else
		{
			setText("\\u221e" + "s");
		}
	}
	
	
	GameLogic logic;
}
