package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.world.level.DialogNode;

public class ActionDialog extends DialogMenu {

	CombatScreen screen;
	CombatState nextState;
	
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;

	static {
		options.add("OK.");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(128, 40, 1280 - 256, 250);
	}
	
	public ActionDialog(CombatScreen screen, String action, CombatState nextState) {
		super(style, new DialogNode(new Array<String>(new String[] { action }), options, optionsEnabled));
		
		this.screen = screen;
		this.nextState = nextState;
	}

	@Override
	public void onDialogChoice(String choice) {
		screen.startState(nextState);
	}

}
