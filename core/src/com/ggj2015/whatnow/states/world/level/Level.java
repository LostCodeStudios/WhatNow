/**
 * 
 */
package com.ggj2015.whatnow.states.world.level;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.utils.AbstractSpriteSheet;
import com.lostcode.javalib.utils.SpriteSheet;

import editor.NonGLSpriteSheet;

/**
 * @author u0847773
 *         The level class
 */
public class Level {
	// FIELDS
	String name;
	Rectangle bounds;
	Vector2 gravity;
	String spriteSheetFile;
	Vector2 cameraInitial;
	transient AbstractSpriteSheet spriteSheet;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	/**
	 * @param name
	 *        The name of the level
	 * @param bounds
	 *        The bounds of the level.
	 * @param gravity
	 *        The world gravity -_-, god knows why
	 * @param spriteSheet
	 *        The path to the sprite sheet XML file.
	 * @param cameraInitial
	 *        initial position of the camera.
	 */
	public Level(String name, Rectangle bounds, Vector2 gravity,
			String spriteSheet, Vector2 cameraInitial, boolean mode) {
		super();
		this.name = name;
		this.bounds = bounds;
		this.gravity = gravity;
		setSpriteSheet(spriteSheet, mode);
		this.cameraInitial = cameraInitial;
	}

	public Level() {
		this.name = "";
		this.bounds = new Rectangle();
		this.gravity = Vector2.Zero.cpy();
		this.spriteSheet = null;
		this.cameraInitial = Vector2.Zero.cpy();
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *        the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the gameObjects
	 */
	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	/**
	 * @param bounds
	 *        the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	/**
	 * @return the gravity
	 */
	public Vector2 getGravity() {
		return gravity;
	}
	/**
	 * @param gravity
	 *        the gravity to set
	 */
	public void setGravity(Vector2 gravity) {
		this.gravity = gravity;
	}
	/**
	 * @return the cameraInitial
	 */
	public Vector2 getCameraInitial() {
		return cameraInitial;
	}
	/**
	 * @param cameraInitial
	 *        the cameraInitial to set
	 */
	public void setCameraInitial(Vector2 cameraInitial) {
		this.cameraInitial = cameraInitial;
	}
	/**
	 * @return the spriteSheet
	 */
	public AbstractSpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	/**
	 * @param spriteSheet
	 *        the spriteSheet to set
	 */
	public void setSpriteSheet(String spriteSheet, boolean mode) {
		this.spriteSheetFile = spriteSheet;
		try {
			if (mode)
				this.spriteSheet =
						SpriteSheet.fromXML(Gdx.files.internal(spriteSheet));
			else
				this.spriteSheet =
						NonGLSpriteSheet.fromXML("..\\core\\assets\\"
								+ spriteSheet);
		} catch (IOException ee) {
			ee.printStackTrace();
		}
	}

	public String getSpriteSheetFile() {
		return this.spriteSheetFile;
	}

}
