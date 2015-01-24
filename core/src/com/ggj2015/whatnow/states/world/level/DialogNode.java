package com.ggj2015.whatnow.states.world.level;

import com.badlogic.gdx.utils.Array;

public class DialogNode {
	Array<String> text;
	Array<String> options;
	Array<DialogNode> next; 
	// these can be null, and are in 1-1 corespondance
	// to the "options" array. 
}