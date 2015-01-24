package com.ggj2015.whatnow;

import com.badlogic.gdx.Gdx;
import com.ggj2015.whatnow.states.MainMenuScreen;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.LogManager;
import com.lostcode.javalib.utils.LogManager.LogType;

public class WhatNow extends Game {
	
	public WhatNow() {
		super();

		title = "Lingering";
		
		width = 1280;
		height = 720;
		
		backgroundRed = 1f;
		backgroundGreen = 1f;
		backgroundBlue = 1f;
		
	}

	@Override
	public void create() {
		super.create();

		Convert.init(32);
		LogManager.init(Gdx.app, LogType.ERROR);
		
		getScreenManager().addScreen(new MainMenuScreen(this, spriteBatch));
	}

	@Override
	protected void loadSounds() {
		
	}

}
