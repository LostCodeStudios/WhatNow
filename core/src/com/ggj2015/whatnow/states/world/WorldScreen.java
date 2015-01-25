package com.ggj2015.whatnow.states.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.lostcode.javalib.Game;

public class WorldScreen extends DialogScreen {

	NowWorld world;
	
	// TODO this constructor will take world arguments to create the desired sub-world
	public WorldScreen(Game game, SpriteBatch spriteBatch, String level) {
		super(game, spriteBatch);
		OrthographicCamera camera = new OrthographicCamera(1280, 720);
		world = new NowWorld(game.getInput(), camera, level, game);
		world.myScreen = this;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void dispose() {
		world.dispose();
	}
	
	@Override
	public void show() {
		world.resume();
	}

	@Override
	public void hide() {
		world.pause();
	}
	
	@Override
	public void render(float delta) {
		world.process();
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
