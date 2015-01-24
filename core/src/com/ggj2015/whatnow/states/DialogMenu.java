package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * An interactive dialog box
 * @author Nathaniel
 *
 */
public abstract class DialogMenu {
	
	Rectangle bounds;
	
	Array<String> text = new Array<String>();
	Array<String> menuOptions = new Array<String>();
	Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	BitmapFont font;
	
	boolean closed = false;
	
	// TODO make these either customizable or constant
	Color backgroundColor = Color.GRAY;
	Color borderColor = Color.BLACK;
	float borderWidth = 4f;
	float textMargin = 8f;
	Color textColor = Color.BLACK;
	Color optionColor = Color.WHITE;
	Color optionColorDisabled = Color.DARK_GRAY;
	Color optionColorSelected = Color.YELLOW;
	
	public DialogMenu(Array<String> text, Array<String> options, Array<Boolean> optionsEnabled) {
		bounds = new Rectangle(50, 50, 300, 400); // TODO variable bounds
		
		this.text.addAll(text);
		this.menuOptions.addAll(options);
		this.optionsEnabled.addAll(optionsEnabled);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ARJULIAN.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
	
	public DialogMenu(Array<String> text, Array<String> options) {
		this(text, options, new Array<Boolean>(true, options.size));
	}
	
	public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin();
		
		// render dialog background
		shapeRenderer.setColor(backgroundColor);
		shapeRenderer.set(ShapeType.Filled);
		
		shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		// render dialog border
		shapeRenderer.setColor(borderColor);
		shapeRenderer.set(ShapeType.Filled);
		
		shapeRenderer.rect(bounds.x, bounds.y, bounds.width, borderWidth); // bottom
		shapeRenderer.rect(bounds.x, bounds.y, borderWidth, bounds.height); // left
		shapeRenderer.rect(bounds.x, bounds.y + bounds.height - borderWidth, bounds.width, borderWidth); // top
		shapeRenderer.rect(bounds.x + bounds.width - borderWidth, bounds.y, borderWidth, bounds.height); // right
		
		shapeRenderer.end();
		
		spriteBatch.begin();
		
		// render dialog text
		float x = bounds.x + borderWidth + textMargin;
		float y = bounds.y + bounds.height - borderWidth - textMargin;
		
		for (String line : text) {
			// TODO wrap paragraphs
			
			font.draw(spriteBatch, line, x, y);
			
			y -= font.getLineHeight();
		}
		
		y -= font.getLineHeight(); // an extra line break
		
		x += 2 * textMargin; // options indented
		
		for (String option : menuOptions) {
			font.draw(spriteBatch, option, x, y);
		}
		
		// TODO render a selection next to the current option
		
		spriteBatch.end();
	}
	
	public void close() {
		this.closed = true;
	}
	
	public boolean isClosed() {
		return this.closed;
	}
	
	public abstract void onDialogChoice(String choice);
	
}
