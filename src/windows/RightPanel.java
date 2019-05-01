package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.GameLogic;

// RJ

public class RightPanel extends JPanel 
{

	public RightPanel(GameLogic logic, MainFrame frame) 
	{
		setPreferredSize(new Dimension((int)(frame.getWidth() / 4), getHeight()));
		
		int boldTextSize = 17;
		int textSize = 14;
		
		timeSlider = setTimeSlider(logic);
		fuelStat = setBoldLabel(String.format("%.2f", logic.getShip().getFuel()) + "%", boldTextSize);
		massStat = setBoldLabel(String.format("%.2f", logic.getShip().getMass()) + " kg", boldTextSize);
		timeStat = setBoldLabel(logic.getTimeLeft() + "s", boldTextSize);
		restartButton = setGameOverButton("Rozpocznij ponownie");
		endButton = setGameOverButton("Zakończ");
		
		add(setColorButton(frame.upperPanel, this));
		add(setLabel("Skala czasu:", textSize));
		add(timeSlider);
		add(setLabel("Stan paliwa:", textSize));
		add(fuelStat);
		add(setLabel("Aktualna masa rakiety:", textSize));
		add(massStat);
		add(setLabel("Pozostały czas:", textSize));
		add(timeStat);
		add(restartButton);
		add(endButton);
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
		JButton button = new JButton("Zmiana koloru interfejsu");
		button.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Color bgColor = JColorChooser.showDialog(null, "Proszę wybrać kolor", Color.WHITE);
				
				upperPanel.setBackground(bgColor);
				rightPanel.setBackground(bgColor);
				rightPanel.timeSlider.setBackground(bgColor);
			}
		});
		
		return button;
	}
	
	JSlider setTimeSlider(GameLogic logic) 
	{
		JSlider slider = new JSlider(-10, 0, 0);
		slider.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				logic.setDT(logic.initDT + slider.getValue());
				System.out.println("dt changed to: " + logic.getDT());
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
	
	JSlider timeSlider;
	JLabel fuelStat, massStat, timeStat;
	JButton restartButton, endButton;
}
