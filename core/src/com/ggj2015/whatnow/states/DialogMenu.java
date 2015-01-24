package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
public abstract class DialogMenu implements InputProcessor {
	
	InputMultiplexer input;
	Rectangle bounds;
	
	Array<String> text = new Array<String>();
	Array<String> menuOptions = new Array<String>(); // TODO combine 3 into 1 dialog node
	Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	int selectedIndex = 0;
	
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
	int textSize = 24;
	
	public DialogMenu(Array<String> text, Array<String> options, Array<Boolean> optionsEnabled) {
		bounds = new Rectangle(50, 50, 300, 400); // TODO variable bounds
		
		this.text.addAll(text);
		this.menuOptions.addAll(options);
		this.optionsEnabled.addAll(optionsEnabled);
		
		if (optionsEnabled.size > 0) {
			while (!optionsEnabled.get(selectedIndex)) {
				selectedIndex++; // don't let the selection start on a disabled menu item
			}
		}
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ARJULIAN.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = textSize;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
	
	public DialogMenu(Array<String> text, Array<String> options) {
		this(text, options, new Array<Boolean>());
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true); // default all enabled
		}
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
		font.setColor(textColor);
		
		for (String line : text) {
			// TODO wrap paragraphs
			
			font.draw(spriteBatch, line, x, y);
			
			y -= font.getLineHeight();
		}
		
		y -= font.getLineHeight(); // an extra line break
		
		x += 2 * textMargin; // options indented
		
		for (int i = 0; i < menuOptions.size; i++) {
			String option = menuOptions.get(i);
			Color color = optionColor;
			
			if (!optionsEnabled.get(i)) {
				color = optionColorDisabled;
			}
			
			if (selectedIndex == i) {
				color = optionColorSelected;
			}
			
			font.setColor(color);
			font.draw(spriteBatch, option, x, y);
			
			y -= font.getLineHeight();
		}
		
		// TODO render a selection next to the current option
		
		spriteBatch.end();
	}
	
	public void show(InputMultiplexer input) {
		input.addProcessor(this);
		this.input = input;
	}
	
	public void close() {
		this.closed = true;
		input.removeProcessor(this);
	}
	
	public boolean isClosed() {
		return this.closed;
	}
	
	void loopSelection() {
		if (selectedIndex < 0) selectedIndex = menuOptions.size - 1;
		else
			selectedIndex = selectedIndex % menuOptions.size; // loop around the menu
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Keys.DOWN) {
			do {
				selectedIndex++;
				loopSelection();
			} while (!optionsEnabled.get(selectedIndex));
			
			return true;
		}
		
		if (keycode == Keys.UP) {
			do {
				selectedIndex--;
				loopSelection();
			} while (!optionsEnabled.get(selectedIndex));
			
			return true;
		}
		
		if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
			onDialogChoice(menuOptions.get(selectedIndex)); // fire the input event
			
			// and close this dialog
			close();
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public abstract void onDialogChoice(String choice);
	
}
