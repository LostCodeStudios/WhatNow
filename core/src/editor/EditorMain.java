package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ggj2015.whatnow.states.world.level.Level;

public class EditorMain {
	public static Dimension SCREEN = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public static void runEditor() {
		JFrame frame = new JFrame("Lingering Editor");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(SCREEN);

		Level base =
				new Level(
						"Untitled",
						new Rectangle(-50, -50, 100, 100),
						new Vector2(0, 0),
						"spritesheet.xml",
						new Vector2(0f, 0f));
		EditorPanel ep = new EditorPanel(base);

		frame.add(ep, BorderLayout.CENTER);
		frame.add(ep.side_panel, BorderLayout.EAST);

		frame.setVisible(true);
	}
}
