package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.dialog.DialogMenu;
import com.ggj2015.whatnow.states.dialog.DialogNode;
import com.ggj2015.whatnow.states.dialog.DialogStyle;
import com.lostcode.javalib.utils.Random;
import com.lostcode.javalib.utils.SoundManager;

public class CombatDialog extends DialogMenu {
	
	CombatScreen screen;
	
	private static final Random RAND = new Random();

	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT.cpy();
	
	private String enemyName;
	private boolean enemyInvincible;
	
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
	
	public CombatDialog(CombatScreen screen, String enemyName, boolean enemyInvincible, boolean canRun) {

		super(style, new DialogNode(text, options, optionsEnabled));
		
		node.optionsEnabled.set(3, canRun);
		
		this.screen = screen;
		this.enemyName = enemyName;
		this.enemyInvincible = enemyInvincible;
	}

	@Override
	public void onDialogChoice(String choice) {
		if (choice.equals("Attack")) {
			int dmg = RAND.nextInt(14000, 20000);
			if (enemyInvincible)
				dmg = 0;
			String damageText = "You swing your sword, dealing " + dmg + " damage! (Critical strike!!)";
			screen.damageEnemy(dmg);
			screen.setPlayerChoice(damageText);
			screen.startState(CombatState.PlayerAction);
			
			SoundManager.playSound("sword");
		} 
		else if (choice.equals("Spell")) {
			screen.showDialog(new SpellDialog(screen, enemyName, enemyInvincible));
		} 
		else if (choice.equals("Item")) {
			screen.showDialog(new ItemDialog(screen, enemyName, enemyInvincible));
		}
		else if (choice.equals("Run")) {
			screen.showDialog(new RunDialog(screen));
		}
	}
}
