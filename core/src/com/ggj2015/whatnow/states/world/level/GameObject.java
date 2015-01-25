package com.ggj2015.whatnow.states.world.level;

import com.badlogic.gdx.math.Vector3;

/**
 * The general level abstraction for tiles, etc.
 * 
 * @author u0847773
 *
 */
public class GameObject {
	// FIELDS
	Vector3 position;
	String spriteKey;
	String tag;
	String group;
	String type;
	String template;
	int layer = 0;
	float scale = 1.0f;

	// Object Oriented Obligations
	/**
	 * @param position
	 * @param spriteKey
	 * @param tag
	 * @param group
	 * @param type
	 * @param template
	 * @param layer
	 * @param scale
	 */
	public GameObject(String template, String spriteKey) {
		this.spriteKey = spriteKey;
		this.template = template;

	}
	
	protected GameObject(){
		this.spriteKey = "";
		this.template = "";
	}
	
	//GETTERSETTER
	/**
	 * @return the position
	 */
	public Vector3 getPosition() {
		return position;
	}



	/**
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @param position
	 *        the position to set
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
	 * @param spriteKey
	 *        the spriteKey to set
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
	 * @param tag
	 *        the tag to set
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
	 * @param group
	 *        the group to set
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
	 * @param type
	 *        the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param type
	 *        the type to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

}
