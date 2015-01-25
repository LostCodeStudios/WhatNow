package com.ggj2015.whatnow;

import com.badlogic.gdx.Gdx;
import com.ggj2015.whatnow.states.MainMenuScreen;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.LogManager;
import com.lostcode.javalib.utils.SoundManager;
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

		Convert.init(128);
		LogManager.init(Gdx.app, LogType.ERROR);
		
		getScreenManager().addScreen(new MainMenuScreen(this, spriteBatch));
		
		if(editing) {
//			EditorMain.generateTestLevel();		
			EditorMain.runEditor();
		}
	}

	@Override
	protected void loadSounds() {
		SoundManager.addSong("Combat", Gdx.files.internal("music/Battle_1.mp3"));
		SoundManager.addSong("Combat-intro", Gdx.files.internal("music/Battle_Intro.mp3"));
		SoundManager.addSong("Shop", Gdx.files.internal("music/Shop_Music.mp3"));
		SoundManager.addSong("Walk", Gdx.files.internal("music/Walk_Back.wav"));
		
		SoundManager.addSound("dragon", Gdx.files.internal("sounds/DragonDie.wav"));
		SoundManager.addSound("footsteps", Gdx.files.internal("sounds/footsteps.wav"));
		SoundManager.addSound("button-press", Gdx.files.internal("sounds/menu-button.wav"));
		SoundManager.addSound("button-select", Gdx.files.internal("sounds/menu-select.wav"));
		SoundManager.addSound("sword", Gdx.files.internal("sounds/SWORDSFX.wav"));
		
		SoundManager.setMusicVolume(1f);
	}

}
