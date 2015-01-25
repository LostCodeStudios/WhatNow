package editor;

import java.awt.Dimension;
import java.awt.Point;

import com.badlogic.gdx.math.Vector3;

public class EditEye {
	public static final float FACTOR = 32.0f;

	Dimension screen;
	Vector3 pos, posTo;

	public EditEye(Dimension screen) {
		this.screen = screen;
		this.pos = new Vector3(0, 0, 1);
	}
	
	public void update() {
		pos.lerp(posTo, 0.05f);
	}

	public Point toScreen(Vector3 v) {
		return new Point((int) (FACTOR * (v.x - pos.x) / (pos.z - v.z))
				+ screen.width / 2,
				(int) (FACTOR * (v.y - pos.y) / (pos.z - v.z)) + screen.height
						/ 2);
	}
	public Vector3 pick(int x, int y) {
		// assume there's a z component
		return new Vector3(pos.z * (x - screen.width / 2) / FACTOR + pos.x,
				pos.z * (y - screen.height / 2) / FACTOR + pos.y, 0);
	}
}
