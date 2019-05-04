package windows;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.GameLogic;

// Z

public class MainFrame extends JFrame implements MouseListener, ComponentListener
{
	
	public MainFrame(GameLogic logic) 
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setMinimumSize(getSize());
		setLocationRelativeTo(null); //centering 
		ResourceBundle windowBundle = ResourceBundle.getBundle("windows.WindowBundle", Locale.getDefault());
		setTitle(windowBundle.getString("title"));
		setLayout(new BorderLayout());
		
		//Choosing language
		LanguageDialog languageDialog= new LanguageDialog(this, logic);
		
		// Here to add Panels!
		upperPanel = new UpperPanel(logic.getShip());
		rightPanel = new RightPanel(logic, this);
		gamePanel = new GamePanel(logic);

		add(upperPanel, BorderLayout.PAGE_START);
		add(rightPanel, BorderLayout.LINE_END);
		add(gamePanel, BorderLayout.CENTER);

		addComponentListener(this);
		addMouseListener(this);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		gamePanel.requestFocus();
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		gamePanel.requestFocus();
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		gamePanel.requestFocus();
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	UpperPanel upperPanel;
	RightPanel rightPanel;
	GamePanel gamePanel;
	
	public static void main(String[] args) 
	{
		Locale.setDefault(new Locale("pl", "PL"));
		GameLogic logic = new GameLogic();
		SwingUtilities.invokeLater(new Runnable() 
		{

			public void run() 
			{
				MainFrame frame = new MainFrame(logic);
			}
		});
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		gamePanel.resize();
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
