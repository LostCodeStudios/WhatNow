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
import com.ggj2015.whatnow.states.world.level.DialogNode;

/**
 * An interactive dialog box
 * @author Nathaniel
 *
 */
public abstract class DialogMenu implements InputProcessor {
	
	InputMultiplexer input;
	
	DialogNode node;
	DialogStyle style;
	
	int selectedIndex = 0;
	
	BitmapFont textFont;
	BitmapFont optionFont;
	
	boolean closed = false;
	
	public DialogMenu(DialogStyle style, DialogNode node) {
		this.style = style;
		
		this.node = node;
		
		if (node.optionsEnabled.size > 0) {
			while (!node.optionsEnabled.get(selectedIndex)) {
				selectedIndex++; // don't let the selection start on a disabled menu item
			}
		}
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ARJULIAN.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = style.textSize;
		textFont = generator.generateFont(parameter);
		
		parameter.size = style.optionTextSize;
		optionFont = generator.generateFont(parameter);
		
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
	
	public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin();
		
		// render dialog background
		shapeRenderer.setColor(style.backgroundColor);
		shapeRenderer.set(ShapeType.Filled);
		
		shapeRenderer.rect(style.bounds.x, style.bounds.y, style.bounds.width, style.bounds.height);
		
		// render dialog border
		shapeRenderer.setColor(style.borderColor);
		shapeRenderer.set(ShapeType.Filled);
		
		shapeRenderer.rect(style.bounds.x, style.bounds.y, style.bounds.width, style.borderWidth); // bottom
		shapeRenderer.rect(style.bounds.x, style.bounds.y, style.borderWidth, style.bounds.height); // left
		shapeRenderer.rect(style.bounds.x, style.bounds.y + style.bounds.height - style.borderWidth, style.bounds.width, style.borderWidth); // top
		shapeRenderer.rect(style.bounds.x + style.bounds.width - style.borderWidth, style.bounds.y, style.borderWidth, style.bounds.height); // right
		
		shapeRenderer.end();
		
		spriteBatch.begin();
		
		// render dialog text
		float x = style.bounds.x + style.borderWidth + style.textMargin;
		float y = style.bounds.y + style.bounds.height - style.borderWidth - style.textMargin;
		textFont.setColor(style.textColor);
		
		for (int i = 0; i < node.text.size; i++) {
			String line = node.text.get(i);
			
			// TODO wrap paragraphs
			
			textFont.draw(spriteBatch, line, x, y);
			
			y -= i < node.text.size - 1 ? textFont.getLineHeight() : optionFont.getLineHeight();
			
			// beneath all text entries, line break with option text size for better aesthetic appeal
		}
		
		y -= optionFont.getLineHeight(); // an extra line break
		
		x += 2 * style.textMargin; // options indented
		
		for (int i = 0; i < node.options.size; i++) {
			String option = node.options.get(i);
			Color color = style.optionColor;
			
			if (!node.optionsEnabled.get(i)) {
				color = style.optionColorDisabled;
			}
			
			if (selectedIndex == i) {
				color = style.optionColorSelected;
			}
			
			optionFont.setColor(color);
			optionFont.draw(spriteBatch, option, x, y);
			
			y -= optionFont.getLineHeight();
		}
		
		// TODO polish: render a selection next to the current option
		
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
		if (selectedIndex < 0) selectedIndex = node.options.size - 1;
		else
			selectedIndex = selectedIndex % node.options.size; // loop around the menu
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Keys.DOWN) {
			do {
				selectedIndex++;
				loopSelection();
			} while (!node.optionsEnabled.get(selectedIndex));
			
			return true;
		}
		
		if (keycode == Keys.UP) {
			do {
				selectedIndex--;
				loopSelection();
			} while (!node.optionsEnabled.get(selectedIndex));
			
			return true;
		}
		
		if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
			onDialogChoice(node.options.get(selectedIndex)); // fire the input event
			
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
