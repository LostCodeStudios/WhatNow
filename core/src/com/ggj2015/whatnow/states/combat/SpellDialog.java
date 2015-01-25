package com.ggj2015.whatnow.states.combat;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.dialog.DialogMenu;
import com.ggj2015.whatnow.states.dialog.DialogNode;
import com.ggj2015.whatnow.states.dialog.DialogStyle;
import com.lostcode.javalib.utils.Random;

public class SpellDialog extends DialogMenu {

	private static final Random RAND = new Random();
	
	private static class SpellData {
		public String name = "";
		public String description = "";
		public int minDamage = 0;
		public int maxDamage = 0;
	}
	
	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT.cpy();
	
	private String enemyName;
	private boolean enemyInvincible;
	
	private static final ObjectMap<String, SpellData> spells = 
			new ObjectMap<String, SpellData>();
	
	static {
		String spellsData = Gdx.files.internal("combat/spells.txt").readString();
		
		Scanner scanner = new Scanner(spellsData);
		while (scanner.hasNext()) {
			SpellData spell = new SpellData();
			
			spell.name = scanner.nextLine();
			spell.description = scanner.nextLine();
			spell.minDamage = scanner.nextInt();
			spell.maxDamage = scanner.nextInt();
			
			options.add(spell.name);
			spells.put(spell.name,  spell);
			
			if (scanner.hasNext())
				scanner.nextLine();
		}
		
		scanner.close();
		
		options.add("Cancel");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(1280 / 2 + 60, 40 + 60, 1280 / 3, 250);
	}
	public SpellDialog(CombatScreen screen, String enemyName, boolean enemyInvincible) {
		super(style, new DialogNode(text, options, optionsEnabled));
		this.screen = screen;
		this.enemyName = enemyName;
		this.enemyInvincible = enemyInvincible;
	}

	@Override
	public void onDialogChoice(String choice) {
		if (choice.equals("__CANCEL__")) {
			screen.showDialog(new CombatDialog(screen, enemyName, enemyInvincible, true));
		}
		else if (choice.equals("Cancel")) {
			screen.showDialog(new CombatDialog(screen, enemyName, enemyInvincible, true));
		}
		else {
			SpellData spell = spells.get(choice);
			int dmg = RAND.nextInt(spell.minDamage, spell.maxDamage);
			if (enemyInvincible) { dmg = 0; }
			if (enemyName.equals("Farm") && spell.name.equals("Fireball"))
				dmg = 2 * RAND.nextInt(spell.minDamage, spell.maxDamage);

			screen.damageEnemy(dmg);
			String tempDes = spell.description + ", dealing " + dmg + " damage!";
			screen.showDialog(new ActionDialog(screen, tempDes, CombatState.EnemyAction));
		}
	}

}
