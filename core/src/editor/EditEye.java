package main;

import java.awt.Point;

import com.badlogic.gdx.math.Vector3;

public class EditEye {
	public static final float FACTOR = 32.0f;

	Vector3 pos;

	public Point toScreen(Vector3 v) {
		return new Point((int) (FACTOR * (v.x - pos.x) / (v.z - pos.z)),
				(int) (FACTOR * (v.y - pos.y) / (v.z - pos.z)));
	}
}
