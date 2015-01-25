package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.level.Level;

public class EditorMain {
	public static Dimension SCREEN = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public static void runEditor() {
		JFrame frame = new JFrame("Lingering Editor");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(SCREEN);

		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("..\\core\\assets"));

		Level level;

		if (jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			FileHandle fh =
					Gdx.files.getFileHandle(jfc.getSelectedFile()
							.getAbsolutePath(), FileType.Absolute);
			Json json = new Json();

			level = json.fromJson(Level.class, fh);

			// ***************** TODO: GET THIS TO WORK via other thread.
			level.setSpriteSheet(level.getSpriteSheetFile(), true);

		}
		else {
			level = new Level(
					"Untitled",
					new Rectangle(-50, -50, 100, 100),
					new Vector2(0, 0),
					"spritesheet.xml",
					new Vector2(0f, 0f),
					true);

		}

		EditorPanel ep = new EditorPanel(level);

		frame.add(ep, BorderLayout.CENTER);
		frame.add(ep.side_panel, BorderLayout.EAST);

		frame.setVisible(true);
	}
}
