package com.ggj2015.whatnow.states.world.level;

import com.badlogic.gdx.math.Vector3;

/**
 * The general level abstraction for tiles, etc.
 * @author u0847773
 *
 */
public class GameObject {
	//FIELDS
	Vector3 position;
	String spriteKey;
	String tag;
	String group;
	String type;
	
	//Object Oriented Obligations
	public GameObject(Vector3 position, String spriteKey, String tag,
			String group, String type) {
		super();
		this.position = position;
		this.spriteKey = spriteKey;
		this.tag = tag;
		this.group = group;
		this.type = type;
	}

	/**
	 * @return the position
	 */
	public Vector3 getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3 position) {
		this.position = position;
	}

	/**
	 * @return the spriteKey
	 */
	public String getSpriteKey() {
		return spriteKey;
	}

	/**
	 * @param spriteKey the spriteKey to set
	 */
	public void setSpriteKey(String spriteKey) {
		this.spriteKey = spriteKey;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
