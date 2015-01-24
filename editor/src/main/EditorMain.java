package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ggj2015.whatnow.states.world.level.Level;

public class EditorMain {
	public static Dimension SCREEN = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public static void main(String[] args) {
		JFrame frame = new JFrame("LingeringEditor ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(SCREEN);

		Level base =
				new Level("Untitled", new Rectangle(0, 0, 100, 100),
						new Vector2(0, 0),
						"C:\\Users\\Oliver\\git\\whatnow\\editor\\assets\\spritesheet.xml", new Vector2(0f, 0f));
		EditorPanel ep = new EditorPanel(base);

		frame.add(ep);

		frame.setVisible(true);
	}
}
