package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.world.level.DialogNode;

public class CombatDialog extends DialogMenu {

	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;
	
	static {
		options.add("Attack");
		options.add("Spell");
		options.add("Item");
		options.add("Run");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(1280 / 2, 40, 1280 / 3, 250);
	}
	
	public CombatDialog(CombatScreen screen) {
		super(DialogStyle.DEFAULT, new DialogNode(text, options, optionsEnabled));
		
		this.screen = screen;
	}

	@Override
	public void onDialogChoice(String choice) {
		if (choice.equals("Attack")) {
			screen.setPlayerChoice("Attack");
			screen.startState(CombatState.PlayerAction);
		} else if (choice.equals("Spell")) {
			screen.showDialog(new SpellDialog(screen));
		} else if (choice.equals("Item")) {
			screen.showDialog(new ItemDialog(screen));
		} else if (choice.equals("Run")) {
			screen.showDialog(new RunDialog(screen));
		}
	}
}