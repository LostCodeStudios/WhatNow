package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class EditorMain {
	public static Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
			
	public static void main(String[] args) {
		JFrame frame = new JFrame("LingeringEditor ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(SCREEN);
		frame.setVisible(true);
	}
}
