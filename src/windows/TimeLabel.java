package windows;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

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
		
		if(thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run()
	{
		if(logic.getTimeLeft() >= 0 && !logic.getGameOver())
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
			setText(windowBundle.getString("infinity"));
		}
	}
	
	// Gets
	public Thread getThread() { return thread; }
	
	// Sets
	public void setThread(Thread thread) { this.thread = thread; }
	
	public void setLogic(GameLogic logic) { this.logic = logic; }
	
	private ResourceBundle windowBundle = ResourceBundle.getBundle("windows.WindowBundle", Locale.getDefault());
	private GameLogic logic;
	private Thread thread = null;
}
