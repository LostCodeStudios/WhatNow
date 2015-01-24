package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.world.level.DialogNode;
import com.lostcode.javalib.utils.Random;

public class RunDialog extends DialogMenu {

	CombatScreen screen;
	
	private static final Array<String> successText = new Array<String>();
	private static final Array<String> failText = new Array<String>();
	
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;
	
	private static final Random RANDOM = new Random();
	
	static {
		successText.add("You successfully flee the enemy.");
		failText.add("You can't escape the enemy!");
		
		options.add("OK.");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(128, 40, 1280 - 256, 250);
	}
	public RunDialog(CombatScreen screen) {
		super(style, new DialogNode(RANDOM.percent(0.25f) ? successText : failText, options, optionsEnabled));
		
		this.screen = screen;
	}

	@Override
	public void onDialogChoice(String choice) {
		screen.getGame().getScreenManager().closeActiveScreen(); // end combat
	}

}