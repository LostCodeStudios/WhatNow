package com.ggj2015.whatnow.states.world.entities.components;

import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.dialog.DialogTree;
import com.lostcode.javalib.entities.components.Component;
import com.lostcode.javalib.entities.components.ComponentManager;

public class DialogComponent implements Component {

	public DialogTree dialogTree;
	DialogScreen screen;
	
	public DialogComponent(DialogScreen screen, String treePath) {
		this.screen = screen;
		dialogTree = new DialogTree(treePath);
	}
	
	public void showDialog() {
		dialogTree.run(screen);
	}
	
	@Override
	public void onAdd(ComponentManager container) {
		
	}

	@Override
	public void onRemove(ComponentManager container) {
		
	}

}
