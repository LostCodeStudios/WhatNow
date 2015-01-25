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
import com.ggj2015.whatnow.states.world.entities.Player;
import com.lostcode.javalib.utils.Random;

public class ItemDialog extends DialogMenu {

	static class ItemData {
		public String name = "";
		public String description = "";
		public String type = "";
		public int minEffect = 0;
		public int maxEffect = 0;
		public String favEnemy = "";
		public int favMin = 0;
		public int favMax = 0;
	}
	
	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final ObjectMap<String, ItemData> items = new ObjectMap<String, ItemData>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT.cpy();
	
	private static final Random RAND = new Random();
	
	private String enemyName;
	private boolean enemyInvincible;
	
	static {
		String itemFile = Gdx.files.internal("combat/items.txt").readString();
		
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
			item.favMin = scanner.nextInt();
			item.favMax = scanner.nextInt();
			
			//if (Player.ITEMS.containsKey(item.name) || (item.name.equals("Water") && Player.ITEMS.containsKey("Filled Bucket"))) {
				items.put(item.name, item);
				options.add(item.name);
			//}
			
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
	public ItemDialog(CombatScreen screen, String enemyName, boolean enemyInvincible) {
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
			ItemData item = items.get(choice);
			int dmg = RAND.nextInt(item.minEffect, item.maxEffect);
			if (item.favEnemy.equals(enemyName)) {
				item.type = "Damage";
				dmg = RAND.nextInt(item.favMin, item.favMax);
			}
			else if (enemyInvincible && item.type.equals("Damage")) {dmg = 0;}
			
			screen.useItem(item.type, dmg);
			String tempDes;
			if (item.type.equals("Damage"))
				 tempDes = item.description + ", dealing " + dmg + " damage!";
			else
				 tempDes = item.description + ", healing you for " + dmg + " health!";
			
			if (item.name.equals("Health Potion") || item.name.equals("Food") || item.name.equals("Winch"))
				Player.removeItem(item.name);
			
			if (item.name.equals("Filled Bucket") || item.name.equals("Water")) {
				Player.removeItem("Filled Bucket");
				Player.addItem("Bucket");
			}
			
			if (item.name.equals("Food"))
				Player.HUNGER = 0;
			if (item.name.equals("Water"))
				Player.THIRST = 0;
			
			if (item.name.equals("Plow") && RAND.percent(20f)) {
				Player.removeItem("Plow");
				tempDes += " The plow broke!";
			}
			
			screen.showDialog(new ActionDialog(screen, tempDes, CombatState.EnemyAction));
		}
	}

}
