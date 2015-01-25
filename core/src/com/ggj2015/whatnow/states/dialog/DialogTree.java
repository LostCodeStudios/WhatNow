package com.ggj2015.whatnow.states.dialog;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class DialogTree {

	ObjectMap<String, DialogNode> dialogNodes = new ObjectMap<String, DialogNode>();
	String firstNode = "";
	
	public DialogTree(String filePath) {
		Json json = new Json();
		
		String mark = "---";
		
		Scanner scanner = new Scanner(Gdx.files.internal("dialog/" + filePath).readString());
		
		firstNode = scanner.nextLine();
		
		while (scanner.hasNext()) {
			String nodeString = "";
			
			while (true) {
				String line = scanner.nextLine();
				
				if (line.contains(mark)) {
					// construct a node now
					String nodeKey = line.substring(3);
					
					DialogNode node = json.fromJson(DialogNode.class, nodeString);
					dialogNodes.put(nodeKey, node);
					
					break;
				} else {
					nodeString += line;
				}
			}
		}
		
		scanner.close();
		
	}
	
	public void run(DialogScreen screen) {
		DialogNode first = dialogNodes.get(firstNode); // start with the first in the file
		
		run(first, screen);
	}
	
	public void run(DialogNode node, final DialogScreen screen) {
		
		screen.showDialog(new DialogMenu(DialogStyle.DEFAULT, node) {

			@Override
			public void onDialogChoice(String choice) {
				if (node.next.size > 0) {
					String next = node.next.get(node.options.indexOf(choice, false));
					
					DialogTree.this.run(DialogTree.this.dialogNodes.get(next), screen);
				} else {
					onFinished();
				}
			}
			
		});
	}
	
	public void onFinished() {
		// override this to hook code to the end of a dialog!!!!!!
	}
	
}
