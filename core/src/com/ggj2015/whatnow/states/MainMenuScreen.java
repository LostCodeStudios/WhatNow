package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.lostcode.javalib.Game;

public class MainMenuScreen extends DialogScreen {

	public MainMenuScreen(Game game, SpriteBatch spriteBatch) {
		super(game, spriteBatch);
		
		// TODO write the real main menu with actual title
		Array<String> text = new Array<String>();
		text.add("What do we do next?");
		
		Array<String> options = new Array<String>();
		options.add("Play");
		
		showDialog(new DialogMenu(text, options) {

			@Override
			public void onDialogChoice(String choice) {
				if (choice.equals("Play")) {
					this.close();
					MainMenuScreen.this.game.getScreenManager().addScreen(new WorldScreen(MainMenuScreen.this.game));
				}
			}
			
		});
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO make a real menu
		if (keycode == Keys.SPACE) {
			game.getScreenManager().addScreen(new WorldScreen(game));
		}
		
		return false;
	}

}
