package com.ggj2015.whatnow.states.dialog;

import com.badlogic.gdx.utils.Array;
import com.sun.istack.internal.Nullable;

public class DialogNode {
	
	public DialogNode() {
		
	}
	
	public DialogNode(Array<String> text, Array<String> options, Array<Boolean> optionsEnabled) {
		this.text.addAll(text);
		this.options.addAll(options);
		this.optionsEnabled.addAll(optionsEnabled);
	}
	
	public Array<String> text = new Array<String>(); // text paragraphs
	public Array<String> options = new Array<String>(); // response options
	public Array<Boolean> optionsEnabled = new Array<Boolean>(); // whether options are enabled (1-1 correspondence)
	
	@Nullable
	public Array<String> next = new Array<String>(); // response next nodes
}