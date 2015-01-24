package main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class CreationPanel extends JPanel {
	private static final long serialVersionUID = 2315263733853810583L;
	
	//need to have a list of every type of object...
	
	//also need to have a bunch of property fields that can be edited.

	public CreationPanel() {
		// we REALLY want to be this size...
		setPreferredSize(new Dimension(200, EditorMain.SCREEN.height));
		setMinimumSize(new Dimension(200, EditorMain.SCREEN.height));
		setMaximumSize(new Dimension(200, EditorMain.SCREEN.height));
	}
}
