package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.GameLogic;
import windows.listeners.RestartListener;

// RJ

public class RightPanel extends JPanel implements Runnable
{

	public RightPanel(GameLogic logic, MainFrame frame) 
	{
		this.logic = logic;
		this.frame = frame;
		setPreferredSize(new Dimension((int)(frame.getWidth() / 4), getHeight()));
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		int boldTextSize = 17;
		int textSize = 14;
		
		timeSlider = setTimeSlider(logic);
		fuelStat = setBoldLabel(String.format("%.2f", logic.getShip().getFuel()) + "%", boldTextSize);
		massStat = setBoldLabel(String.format("%.2f", logic.getShip().getMass()) + " kg", boldTextSize);
		timeStat = new TimeLabel(logic, boldTextSize);
		restartButton = setGameOverButton(windowBundle.getString("restart"));
		endButton = setGameOverButton(windowBundle.getString("end"));
		
		endButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		restartButton.addActionListener(new RestartListener(logic, frame));
		
		add(setColorButton(frame.upperPanel, this));
		add(setLabel(windowBundle.getString("timeScale"), textSize));
		add(timeSlider);
		add(setLabel(windowBundle.getString("fuel"), textSize));
		add(fuelStat);
		add(setLabel(windowBundle.getString("mass"), textSize));
		add(massStat);
		add(setLabel(windowBundle.getString("timeLeft"), textSize));
		add(timeStat);
		add(restartButton);
		add(endButton);
		
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			fuelStat.setText(String.format("%.2f", logic.getShip().getFuel()) + "%");
			massStat.setText(String.format("%.2f", logic.getShip().getMass()) + " kg");
			
			try
			{
				Thread.sleep(SLEEP_TIME);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	// Making label with certain font size
	JLabel setLabel(String text, int fontSize) 
	{
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, fontSize));
		
		return label;
	}
	
	// Making bold label with certain font size
	JLabel setBoldLabel(String text, int fontSize) 
	{
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, fontSize));
		label.setPreferredSize(new Dimension(150, 75));
		label.setVerticalTextPosition(JLabel.TOP);
		label.setVerticalAlignment(JLabel.TOP);
		
		return label;
	}
	
	JButton setColorButton(UpperPanel upperPanel, RightPanel rightPanel) 
	{
		JButton button = new JButton(windowBundle.getString("color"));
		button.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Color bgColor = JColorChooser.showDialog(null, windowBundle.getString("chooseColor"), Color.WHITE);
				
				upperPanel.setBackground(bgColor);
				rightPanel.setBackground(bgColor);
				rightPanel.timeSlider.setBackground(bgColor);
				frame.gamePanel.requestFocus();
			}
		});
		
		return button;
	}
	
	JSlider setTimeSlider(GameLogic logic) 
	{
		JSlider slider = new JSlider((int)-(logic.initDT - 1), 0, 0);
		slider.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				logic.setDT(logic.initDT + slider.getValue());
				frame.gamePanel.requestFocus();
			}
		});
		
		return slider;
	}
	
	// Setting up the game-over button
	JButton setGameOverButton(String text) 
	{
		JButton button = new JButton(text);
		button.setEnabled(false);
		
		return button;
	}
	
	// Gets
	public JButton getEndButton() { return endButton; }
	
	public JButton getRestartButton() { return restartButton; }
	
	private MainFrame frame;
	private GameLogic logic;
	private JSlider timeSlider;
	private JLabel fuelStat, massStat;
	private TimeLabel timeStat;
	private JButton restartButton, endButton;
	private ResourceBundle windowBundle = ResourceBundle.getBundle("windows/WindowBundle", Locale.getDefault());
	// Sleep time in ms
	final int SLEEP_TIME = 500;
}
