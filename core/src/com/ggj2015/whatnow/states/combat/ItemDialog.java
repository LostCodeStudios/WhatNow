package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.world.level.DialogNode;

public class ItemDialog extends DialogMenu {

	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;
	
	static {
		options.add("Throw rock");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(1280 / 2 + 60, 40 + 60, 1280 / 3, 250);
	}
	public ItemDialog(CombatScreen screen) {
		super(style, new DialogNode(text, options, optionsEnabled));
		
		this.screen = screen;
	}

	@Override
	public void onDialogChoice(String choice) {
		if (choice.equals("__CANCEL__")) {
			screen.showDialog(new CombatDialog(screen));
		} else if (choice.equals("Throw rock")) {
			screen.setPlayerChoice("Throw rock");
			screen.startState(CombatState.PlayerAction);
		}
	}

}
