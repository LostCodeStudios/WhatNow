package com.ggj2015.whatnow.states.world.level;

import com.badlogic.gdx.utils.Array;
import com.sun.istack.internal.Nullable;

public class DialogNode {
	public Array<String> text = new Array<String>(); // text paragraphs
	public Array<String> options = new Array<String>(); // response options
	public Array<Boolean> optionsEnabled = new Array<Boolean>(); // whether options are enabled (1-1 correspondence)
	
	@Nullable
	public Array<DialogNode> next = new Array<DialogNode>();
	// these can be null, and are in 1-1 correspondence
	// to the "options" array. 
}