package com.ggj2015.whatnow;

import com.badlogic.gdx.Gdx;
import com.ggj2015.whatnow.states.MainMenuScreen;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.LogManager;
import com.lostcode.javalib.utils.LogManager.LogType;

import editor.EditorMain;

public class WhatNow extends Game {
	
	public WhatNow() {
		super();

		title = "Lingering";
		
		width = 1280;
		height = 720;
		
		backgroundRed = 0f;
		backgroundGreen = 0f;
		backgroundBlue = 0f;
		
	}

	boolean editing = false;
	public WhatNow(boolean b) {
		this();

		this.editing = true;
	}

	@Override
	public void create() {
		super.create();

		Convert.init(32);
		LogManager.init(Gdx.app, LogType.ERROR);
		
		getScreenManager().addScreen(new MainMenuScreen(this, spriteBatch));
		
		if(editing) {
//			EditorMain.generateTestLevel();		
			EditorMain.runEditor();
		}
	}

	@Override
	protected void loadSounds() {
		
	}

}
