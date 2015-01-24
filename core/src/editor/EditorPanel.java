package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;
import com.lostcode.javalib.utils.SpriteSheet;

public class EditorPanel extends JPanel implements MouseListener,
		MouseMotionListener, Runnable {
	private static final long serialVersionUID = 1982999939519987834L;

	Color selectColor = new Color(255, 255, 180, 100);
	BufferedImage fullImage;
	Point startPress, endPress;
	EditEye eye;

	ArrayList<GameObject> selected = new ArrayList<GameObject>();

	// all of the data lives here.
	Level level;
	// ... except the data that secretly lives here
	Map<GameObject, GObjEditData> data =
			new HashMap<GameObject, GObjEditData>();

	public EditorPanel(Level l) {
		this.level = l;

		init();
	}

	private void init() {
		BufferedImage full = null;
		try {
			System.out.println("C:\\Users\\Oliver\\git\\whatnow\\core\\assets\\"+level.getSpriteSheetFile());
			full = ImageIO.read(new File("C:\\Users\\Oliver\\git\\whatnow\\core\\assets\\"+level.getSpriteSheetFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		fullImage = full;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (Exception e) {}

			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		SpriteSheet ss = level.getSpriteSheet();

		for (GameObject o : level.getGameObjects()) {
			Point p = eye.toScreen(o.getPosition());
			TextureRegion r = ss.getRegion(o.getSpriteKey());
			float s = o.getScale();
			int w = r.getRegionWidth(), h = r.getRegionHeight();

			g.drawImage(fullImage, p.x - (int) (s * w / 2), p.y
					- (int) (s * h / 2), (int) (s * w), (int) (s * h),
					r.getRegionX(), r.getRegionY(), w, h, this);
			// draw region from sprite sheet
		}

		// draw pressed stuff

		// and pressing mechanism
		if (startPress != null) {
			g.setColor(selectColor);
			g.drawRoundRect(Math.min(startPress.x, endPress.x),
					Math.min(startPress.y, endPress.y),
					Math.abs(startPress.x - endPress.x),
					Math.abs(startPress.y - endPress.y), 10, 10);
		}

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		endPress = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPress = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		endPress = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
