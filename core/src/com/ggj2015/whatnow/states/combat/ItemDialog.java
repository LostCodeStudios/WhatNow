package com.ggj2015.whatnow.states.combat;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.world.level.DialogNode;
import com.lostcode.javalib.utils.Random;

public class ItemDialog extends DialogMenu {

	static class ItemData {
		public String name = "";
		public String description = "";
		public String type = "";
		public int minEffect = 0;
		public int maxEffect = 0;
		public String favEnemy = "";
	}
	
	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final ObjectMap<String, ItemData> items = new ObjectMap<String, ItemData>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;
	
	private static final Random RAND = new Random();
	
	static {
		String itemFile = Gdx.files.internal("combat/items.txt").readString();
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(itemFile);
		
		while (scanner.hasNext()) {
			ItemData item = new ItemData();
			
			item.name = scanner.nextLine();
			item.description = scanner.nextLine();
			item.type = scanner.nextLine();
			item.minEffect = scanner.nextInt();
			item.maxEffect = scanner.nextInt();
			scanner.nextLine();
			item.favEnemy = scanner.nextLine();
			item.favEnemy = item.favEnemy.trim();
			if (!item.favEnemy.equals("None")) {
				item.type = "Damage";
				item.minEffect = scanner.nextInt();
				item.maxEffect = scanner.nextInt();
			}
			
			items.put(item.name, item);
			options.add(item.name);
			
			if (scanner.hasNext())
				scanner.nextLine();
		}
		
		options.add("Cancel");
		
		for (int i = 0; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		style.bounds = new Rectangle(1280 / 2 + 60, 40 + 60, 1280 / 3, 300);
	}
	public ItemDialog(CombatScreen screen) {
		super(style, new DialogNode(text, options, optionsEnabled));
		this.screen = screen;
	}

	@Override
	public void onDialogChoice(String choice) {
		if (choice.equals("__CANCEL__")) {
			screen.showDialog(new CombatDialog(screen));
		}
		else if (choice.equals("Cancel")) {
			screen.showDialog(new CombatDialog(screen));
		}
		else {
			ItemData item = items.get(choice);
			int dmg = RAND.nextInt(item.minEffect, item.maxEffect);
			screen.useItem(item.type, dmg);
			if (item.type == "Damage")
				item.description += ", dealing " + dmg + " damage!";
			else
				item.description += ", healing you for " + dmg + " health!";
			screen.showDialog(new ActionDialog(screen, item.description, CombatState.EnemyAction));
		}
	}

}
