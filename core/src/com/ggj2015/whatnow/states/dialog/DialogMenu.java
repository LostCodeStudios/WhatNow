package com.ggj2015.whatnow.states.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * An interactive dialog box
 * @author Nathaniel
 *
 */
public abstract class DialogMenu implements InputProcessor {
	
	InputMultiplexer input;
	
	protected DialogNode node;
	DialogStyle style;
	
	int selectedIndex = 0;
	
	BitmapFont textFont;
	BitmapFont optionFont;
	
	boolean closed = false;
	
	TextureRegion borderCorner;
	TextureRegion borderMid;
	TextureRegion paper;
	
	public DialogMenu(DialogStyle style, DialogNode node) {
		borderCorner = new TextureRegion(new Texture(Gdx.files.internal("sprites/border_corner.png")), 1, 1, 32, 32);
		borderMid = new TextureRegion(new Texture(Gdx.files.internal("sprites/border_mid.png")), 1, 1, 32, 32);
		paper = new TextureRegion(new Texture(Gdx.files.internal("sprites/dialogue_paper.png")), 1, 1, 32, 32);
		
		this.style = style;
		
		this.node = node;
		
		if (node.optionsEnabled != null && node.optionsEnabled.size > 0) {
			while (!node.optionsEnabled.get(selectedIndex)) {
				selectedIndex++; // don't let the selection start on a disabled menu item
			}
		} else {
			this.node.optionsEnabled = new Array<Boolean>();
			for (int i = 0; i < node.options.size; i++) {
				node.optionsEnabled.add(true);
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
		spriteBatch.begin();
		
		// render dialog bg
		
		float y = style.bounds.y;
		float x = 0;
		for (int i = 0; i < style.bounds.height / 32; i++) {
			
			x = style.bounds.x;
			for (int j = 0; j < style.bounds.width / 32; j++) {
				
				//bottom
				spriteBatch.draw(paper, x, y);
				
				x += 32;
				
				if (x + 32 > style.bounds.x + style.bounds.width) {
					x -= (x + 32) - (style.bounds.x + style.bounds.width);
				}
			}
			
			y += 32;
			
			if (y + 32 > style.bounds.y + style.bounds.height) {
				y -= (y + 32) - (style.bounds.y + style.bounds.height);
			}
			
		}
		
		// render dialog border
		
		// edges
		
		y = style.bounds.y;
		for (int i = 0; i < style.bounds.height / 32; i++) {
			
			//left
			spriteBatch.draw(borderMid, style.bounds.x - 12, y);
			
			//right
			spriteBatch.draw(borderMid, style.bounds.x + style.bounds.width - 20, y, 16, 16, 32, 32, 1f, 1f, 180f);
			
			y += 32;
			
			if (y + 32 > style.bounds.y + style.bounds.height) {
				y -= (y + 32) - (style.bounds.y + style.bounds.height);
			}
		}
		
		x = style.bounds.x;
		for (int i = 0; i < style.bounds.width / 32; i++) {
			
			//bottom
			spriteBatch.draw(borderMid, x, style.bounds.y - 12, 16, 16, 32, 32, 1f, 1f, 90f);
			
			//top
			spriteBatch.draw(borderMid, x, style.bounds.y + style.bounds.height - 20, 16, 16, 32, 32, 1f, 1f, 270f);
			
			x += 32;
			
			if (x + 32 > style.bounds.x + style.bounds.width) {
				x -= (x + 32) - (style.bounds.x + style.bounds.width);
			}
		}
		
		// corners 
		spriteBatch.draw(borderCorner, style.bounds.x - 12, style.bounds.y - 20 + style.bounds.height, 16, 16, 32, 32, 1f, 1f, 0f);
		spriteBatch.draw(borderCorner, style.bounds.x + style.bounds.width - 20,
				style.bounds.y - 20 + style.bounds.height, 16, 16, 32, 32, 1f, 1f, 270f);
		spriteBatch.draw(borderCorner, style.bounds.x - 12,
				style.bounds.y - 12, 16, 16, 32, 32, 1f, 1f, 90f);
		spriteBatch.draw(borderCorner, style.bounds.x + style.bounds.width - 20,
				style.bounds.y - 12, 16, 16, 32, 32, 1f, 1f, 180f);
		
		// render dialog text
		x = style.bounds.x + style.borderWidth + style.textMargin;
		y = style.bounds.y + style.bounds.height - style.borderWidth - style.textMargin;
		textFont.setColor(style.textColor);
		
		for (int i = 0; i < node.text.size; i++) {
			String line = node.text.get(i);
			
			TextBounds size = textFont.drawWrapped(spriteBatch, line, 
					x, y, style.bounds.width - 2 * style.textMargin);
			
			y -= size.height;
			
			y -= textFont.getLineHeight();
			
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
			TextBounds size = optionFont.drawWrapped(spriteBatch, option, x, y, style.bounds.width - 4 * style.textMargin);
			
			y -= size.height;
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
		
		// special cancel event when "ESCAPE" is pressed
		if (keycode == Keys.ESCAPE && style.allowCancel) {
			onDialogChoice("__CANCEL__");
			
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
