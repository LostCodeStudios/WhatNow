package com.ggj2015.whatnow.states.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class DialogStyle {
	
	private static final float DEFAULT_WIDTH = 1280 / 3;
	private static final float DEFAULT_HEIGHT = 720 / 3;
	
	public static final DialogStyle DEFAULT = new DialogStyle();
	
	public Rectangle bounds = new Rectangle(
			1280 / 2 - DEFAULT_WIDTH / 2,
			720 / 2 - DEFAULT_HEIGHT / 2,
			DEFAULT_WIDTH,
			DEFAULT_HEIGHT);
	
	public Color backgroundColor = Color.GRAY;
	public Color borderColor = Color.BLACK;
	
	public float borderWidth = 4f;
	public float textMargin = 16f;

	public int textSize = 24;
	public int optionTextSize = 24;
	
	public Color textColor = Color.BLACK;
	public Color optionColor = Color.WHITE;
	public Color optionColorDisabled = Color.DARK_GRAY;
	public Color optionColorSelected = Color.YELLOW;
	
	public boolean allowCancel = false;
	
	public DialogStyle cpy() {
		DialogStyle copy = new DialogStyle();
		
		copy.bounds = new Rectangle(bounds);
		copy.backgroundColor = backgroundColor.cpy();
		copy.borderColor = borderColor.cpy();
		copy.borderWidth = borderWidth;
		copy.textMargin = textMargin;
		copy.textSize = textSize;
		copy.optionTextSize = optionTextSize;
		copy.textColor = textColor;
		copy.optionColor = optionColor;
		copy.optionColorDisabled = optionColorDisabled;
		copy.optionColorSelected = optionColorSelected;
		copy.allowCancel = allowCancel;
		
		return copy;
	}
	
}
