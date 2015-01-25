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
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;
import com.lostcode.javalib.utils.SpriteSheet;

public class EditorPanel extends JPanel implements MouseListener,
		MouseMotionListener, Runnable {
	private static final long serialVersionUID = 1982999939519987834L;

	Color selectColor = new Color(255, 205, 160, 100);
	BufferedImage fullImage;
	Point startPress, endPress;
	EditEye eye;

	ArrayList<GameObject> selected = new ArrayList<GameObject>();

	Queue<String> buildQueue = new LinkedList<String>();

	// all of the data lives here.
	Level level;
	// ... except the data that secretly lives here
	Map<GameObject, GObjEditData> data =
			new HashMap<GameObject, GObjEditData>();

	CreationPanel side_panel;

	public EditorPanel(Level l) {
		this.level = l;

		side_panel = new CreationPanel(this);
		eye = new EditEye(EditorMain.SCREEN);

		setBackground(Color.BLACK);
		addMouseListener(this);
		addMouseMotionListener(this);

		new Thread(this).start();

		init();
	}

	private void init() {
		BufferedImage full = null;
		try {
			full =
					ImageIO.read(new File(
							"C:\\Users\\Oliver\\git\\whatnow\\core\\assets\\"
									+ level.getSpriteSheet().getTexturePath()));

			System.out
					.println("C:\\Users\\Oliver\\git\\whatnow\\core\\assets\\"
							+ level.getSpriteSheetFile());

			JOptionPane.showMessageDialog(null, full);
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

		int offset = 0;

		for (String s : buildQueue) {
			g.setColor(Color.RED);
			g.fillOval(10 + offset, 5, 25, 25);
			offset += 30;
		}

		for (GameObject o : level.getGameObjects()) {
			Point p = eye.toScreen(o.getPosition());
			TextureRegion r = ss.getRegion(o.getSpriteKey());
			float s = o.getScale();
			s = 1;
			int w = r.getRegionWidth(), h = r.getRegionHeight();

			// System.out.printf("%d, %d, %d, %d\n", p.x - (int) (s * w / 2), p.y
			// - (int) (s * h / 2), (int) (s * w), (int) (s * h));

			g.fillRect(p.x - (int) (s * w / 2), p.y
					- (int) (s * h / 2), (int) (s * w), (int) (s * h));
			// g.drawImage(fullImage, p.x - (int) (s * w / 2), p.y
			// - (int) (s * h / 2), (int) (s * w), (int) (s * h),
			// r.getRegionX(), r.getRegionY(), w, h, this);
			g.drawImage(fullImage, p.x - (int) (s * w / 2), p.y
					- (int) (s * h / 2), (int) (s * w), (int) (s * h), this);
			// draw region from sprite sheet
		}

		// draw pressed stuff

		// and pressing mechanism
		if (startPress != null && endPress != null) {
			g.setColor(Methods.getColor(selectColor, 255));
			int a = Math.min(startPress.x, endPress.x), b =
					Math.min(startPress.y, endPress.y), c =
					Math.abs(startPress.x - endPress.x), d =
					Math.abs(startPress.y - endPress.y);
			g.drawRoundRect(a, b, c, d, 10, 10);
			g.setColor(selectColor);
			g.fillRoundRect(a, b, c, d, 10, 10);
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

	}
	@Override
	public void mousePressed(MouseEvent e) {
		startPress = e.getPoint();
		endPress = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		endPress = null;
		startPress = null;

		if (!buildQueue.isEmpty()) {
			String str = buildQueue.poll();

			GameObject obj = new GameObject(str, str);

			obj.setPosition(eye.pick(e.getX(), e.getY()));

			if (side_panel.align.isSelected()) {
				obj.getPosition().x = (int) Math.round(obj.getPosition().x);
				obj.getPosition().y = (int) Math.round(obj.getPosition().y);
			}
			System.out.println(obj.getPosition());

			level.getGameObjects().add(obj);
		}
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
