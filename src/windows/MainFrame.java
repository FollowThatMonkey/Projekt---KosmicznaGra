package windows;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setMinimumSize(getSize());
		setTitle("Kosmiczna gra");
		setLayout(new BorderLayout());
		
		// Here to add Panels!
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
	}
}
