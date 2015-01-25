package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.level.GameObject;
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
						new Rectangle(0, 0, 100, 100),
						new Vector2(0, 0),
						"spritesheet.xml",
						new Vector2(0f, 0f));
		EditorPanel ep = new EditorPanel(base);

		frame.add(ep, BorderLayout.CENTER);
		frame.add(ep.side_panel, BorderLayout.EAST);

		frame.setVisible(true);
	}

	public static void generateTestLevel() {
		Level base =
				new Level(
						"Test Level",
						new Rectangle(0, 0, 100, 100),
						new Vector2(0, 0),
						"spritesheet.xml",
						new Vector2(0f, 0f));

		base.getGameObjects().add(
				new GameObject("Player", "Player"));

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				base.getGameObjects().add(
						new GameObject("Tile", "Tile"));

		Json lol = new Json();
		FileHandle fh = Gdx.files.local("intro.level");
		fh.writeString(lol.toJson(base), false);
	}
}
