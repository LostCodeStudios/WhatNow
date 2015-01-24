package com.ggj2015.whatnow.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.states.InputScreen;

/**
 * A screen that will display dialogs and dialog menus in addition to performing its basic function
 * @author Nathaniel
 *
 */
public class DialogScreen extends InputScreen {

	protected SpriteBatch spriteBatch;
	protected ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	Array<DialogMenu> dialogs = new Array<DialogMenu>();
	
	public DialogScreen(Game game, SpriteBatch spriteBatch) {
		super(game);
		
		this.spriteBatch = spriteBatch;
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
	
	@Override
	public void show() {
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
	}
	
	public void showDialog(DialogMenu dialog) {
		dialogs.add(dialog);
		dialog.show(game.getInput());
	}
	
	@Override
	public void render(float delta) {
		for (int i = dialogs.size - 1; i >= 0; i--) {
			DialogMenu dialog = dialogs.get(i);
			
			if (dialog.isClosed()) {
				dialogs.removeIndex(i); // remove closed dialogs
			}
		}
		
		for (DialogMenu dialog : dialogs) {
			dialog.render(shapeRenderer, spriteBatch);
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

}
