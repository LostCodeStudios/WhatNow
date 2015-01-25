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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

	Vector3 frozenCamera;// for scrolling purpose

	HashSet<GameObject> selected = new HashSet<GameObject>();
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

		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);

		new Thread(this).start();

		init();
	}

	private void init() {
		BufferedImage full = null;
		try {
			System.out.println(new File(
					"..\\core\\assets\\"
							+ level.getSpriteSheet().getTexturePath())
					.getAbsolutePath());
			full =
					ImageIO.read(new File(
							"..\\core\\assets\\"
									+ level.getSpriteSheet().getTexturePath()));
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
			eye.update();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		SpriteSheet ss = level.getSpriteSheet();

		int offset = 0;

		Rectangle b = level.getBounds();

		for (int i = (int) b.x; i < b.width + b.x; i++)
		{
			Vector3 p1 = new Vector3(i, b.y, 0), p2 =
					new Vector3(i, b.y + b.height, 0);
			Point s1 = eye.toScreen(p1), s2 = eye.toScreen(p2);
			g.drawLine(s1.x, s1.y, s2.x, s2.y);
		}
		for (int j = (int) b.y; j < b.height + b.y; j++) {
			Vector3 p1 = new Vector3(b.x, j, 0), p2 =
					new Vector3(b.x + b.width, j, 0);
			Point s1 = eye.toScreen(p1), s2 = eye.toScreen(p2);
			g.drawLine(s1.x, s1.y, s2.x, s2.y);
		}

		for (String s : buildQueue) {
			g.setColor(Color.RED);
			g.drawOval(10 + offset, 5, 25, 25);
			offset += 30;
		}

		for (GameObject o : level.getGameObjects()) {
			Point p = eye.toScreen(o.getPosition());
			TextureRegion r = ss.getRegion(o.getSpriteKey());

			if (r == null)
				continue;

			float s = o.getScale();
			int w = r.getRegionWidth(), h = r.getRegionHeight();
			int x = r.getRegionX(), y = r.getRegionY();

			g.drawImage(fullImage, p.x - (int) (s * w / 2), p.y
					- (int) (s * h / 2), p.x + (int) (s * w / 2), p.y
					+ (int) (s * h / 2),
					x, y, x + w, y + h, this);
			// draw region from sprite sheet

			data.get(o).drawX = p.x;
			data.get(o).drawY = p.y;

			if (selected.contains(o)) {
				g.setColor(Methods.getColor(selectColor, 200));
				g.fillOval(p.x - 5, p.y - 5, 10, 10);
			}
		}

		// draw pressed stuff

		// and pressing mechanism
		if (startPress != null && endPress != null && frozenCamera == null) {
			g.setColor(Methods.getColor(selectColor, 255));
			int a = Math.min(startPress.x, endPress.x), q =
					Math.min(startPress.y, endPress.y), c =
					Math.abs(startPress.x - endPress.x), d =
					Math.abs(startPress.y - endPress.y);
			g.drawRoundRect(a, q, c, d, 10, 10);
			g.setColor(selectColor);
			g.fillRoundRect(a, q, c, d, 10, 10);
		}

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		endPress = e.getPoint();
		if (e.isMetaDown())
			eye.posTo =
					frozenCamera.cpy().sub((endPress.x - startPress.x)
							/ EditEye.FACTOR,
							(endPress.y - startPress.y) / EditEye.FACTOR, 0);
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
		if (e.isMetaDown())
			frozenCamera = eye.pos.cpy();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!e.isMetaDown() && !e.isAltDown()) {
			selected.clear();
			side_panel.properties.update();
		}

		if (!e.isMetaDown()) {
			for (GameObject o : level.getGameObjects()) {
				GObjEditData dat = data.get(o);

				if (Methods.in(dat.drawX, startPress.x, endPress.x)
						&& Methods.in(dat.drawY, startPress.y, endPress.y)) {
					selected.add(o);
				}
			}
		}

		side_panel.properties.update();

		endPress = null;
		frozenCamera = null;
		startPress = null;

		if (!buildQueue.isEmpty()) {
			String str = buildQueue.poll();

			GameObject obj = new GameObject(str, str);

			obj.setPosition(eye.pick(e.getX(), e.getY()));
			if (side_panel.align.isSelected()) {
				obj.getPosition().x = (int) Math.round(obj.getPosition().x);
				obj.getPosition().y = (int) Math.round(obj.getPosition().y);
			}

			GObjEditData datum = new GObjEditData();
			data.put(obj, datum);

			level.getGameObjects().add(obj);
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
