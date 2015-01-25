/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author MadcowD, Checkmate50
 *
 */
public final class Player {
	
	//FIELDS
	public static float AGE = 0;
	public static int HEALTH = 0;
	public static int MONEY = 87000;
	public static HashMap<String, Integer> ITEMS = new HashMap<String, Integer>();
	
	//METHODS
	public static void addItem(String item) {
		int add = ITEMS.containsKey(item) ? ITEMS.get(item) : 0;
		ITEMS.put(item, add + 1);
	}
	
	public static void removeItem(String item) {
		if (!(ITEMS.containsKey(item)))
			return;
		int add = ITEMS.get(item);
		if (add == 1)
			ITEMS.remove(item);
		else
			ITEMS.put(item, add - 1);
	}
	
}
