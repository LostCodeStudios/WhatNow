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
		
		title = "What do we do now?";
		
		width = 1280;
		height = 720;
		
		backgroundRed = 0f;
		backgroundGreen = 0f;
		backgroundBlue = 0f;
		
	}

	@Override
	public void create() {
		super.create();

		Convert.init(32);
		LogManager.init(Gdx.app, LogType.ERROR);
		
		getScreenManager().addScreen(new MainMenuScreen(this));
	}

	@Override
	protected void loadSounds() {
		
	}

}
