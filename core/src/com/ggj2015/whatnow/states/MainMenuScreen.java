package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Input.Keys;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.states.InputScreen;

public class MainMenuScreen extends InputScreen {

	public MainMenuScreen(Game game) {
		super(game);
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
