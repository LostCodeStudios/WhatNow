package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.DialogMenu;
import com.ggj2015.whatnow.states.DialogStyle;
import com.ggj2015.whatnow.states.combat.CombatScreen.CombatState;
import com.ggj2015.whatnow.states.world.level.DialogNode;

public class ItemDialog extends DialogMenu {

	static class ItemData {
		static enum EffectType {
			Heal,
			Damage
		}
		
		public String name = "";
		public String description = "";
		public EffectType type = EffectType.Heal;
		public int minEffect = 0;
		public int maxEffect = 0;
	}
	
	CombatScreen screen;
	
	private static final Array<String> text = new Array<String>();
	private static final Array<String> options = new Array<String>();
	private static final Array<Boolean> optionsEnabled = new Array<Boolean>();
	
	private static final ObjectMap<String, ItemData> items = new ObjectMap<String, ItemData>();
	
	private static final DialogStyle style = DialogStyle.DEFAULT;
	
	static {
		String itemFile = Gdx.files.internal("combat/items.txt").readString();
		
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
		} else {
			screen.setPlayerChoice(choice);
			screen.startState(CombatState.PlayerAction);
		}
	}

}
