package com.ggj2015.whatnow.states.world.level;

import com.badlogic.gdx.utils.Array;
import com.sun.istack.internal.Nullable;

public class DialogNode {
	public Array<String> text;
	public Array<String> options;
	@Nullable
	public Array<DialogNode> next; 
	// these can be null, and are in 1-1 corespondance
	// to the "options" array. 
}