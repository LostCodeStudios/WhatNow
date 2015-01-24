package editor;

import java.awt.Point;

import com.badlogic.gdx.math.Vector3;

public class EditEye {
	public static final float FACTOR = 32.0f;

	Vector3 pos;

	public Point toScreen(Vector3 v) {
		return new Point((int) (FACTOR * (v.x - pos.x) / (v.z - pos.z)),
				(int) (FACTOR * (v.y - pos.y) / (v.z - pos.z)));
	}
	
	public Vector3 pick(int x, int y) {
		//assume there's a z component
//		return new Vector3(,0);
	}
}
