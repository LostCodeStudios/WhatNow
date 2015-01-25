package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.combat.CombatScreen;
import com.ggj2015.whatnow.states.dialog.DialogMenu;
import com.ggj2015.whatnow.states.dialog.DialogNode;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.dialog.DialogStyle;
import com.ggj2015.whatnow.states.dialog.DialogTree;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.lostcode.javalib.Game;

public class MainMenuScreen extends DialogScreen {

	public MainMenuScreen(Game game, final SpriteBatch spriteBatch) {
		super(game, spriteBatch);
		
		// TODO polish: add sprites, large title, stylization etc.
		
		Array<String> text = new Array<String>();
		text.add("Lingering");
		
		Array<String> options = new Array<String>();
		options.add("New Game");
		options.add("Load Last Save");
		options.add("Test fight"); // TODO remove this
		options.add("Options");
		options.add("Quit");
		
		Array<Boolean> optionsEnabled = new Array<Boolean>();
		optionsEnabled.add(false); // disable new game
		for (int i = 1; i < options.size; i++) {
			optionsEnabled.add(true);
		}
		
		DialogNode menuNode = new DialogNode();
		menuNode.text.addAll(text);
		menuNode.options.addAll(options);
		menuNode.optionsEnabled.addAll(optionsEnabled);
		
		// TODO remove this
		Json json = new Json();
		System.out.println(json.prettyPrint(menuNode));
		
		DialogTree tree = new DialogTree("wizard.json"){
			@Override
			public void onFinished() {
				MainMenuScreen.this.game.getScreenManager().addScreen(new WorldScreen(MainMenuScreen.this.game, spriteBatch,"overworld.lol"));
			}
		};
		tree.run(this);
		
//		showDialog(new DialogMenu(DialogStyle.DEFAULT, menuNode) {
//
//			@Override
//			public void onDialogChoice(String choice) {
//				if (choice.equals("Load Last Save")) {
//					// TODO make the world for real
//					MainMenuScreen.this.game.getScreenManager().addScreen(new WorldScreen(MainMenuScreen.this.game));
//				}
//				else if (choice.equals("Test fight")) {
//					MainMenuScreen.this.game.getScreenManager().addScreen(new CombatScreen(MainMenuScreen.this.game, MainMenuScreen.this.spriteBatch));
//				}
//				else if (choice.equals("Options")) {
//					// TODO make an options screen
//				}
//				else if (choice.equals("Quit")) {
//					Gdx.app.exit();
//				}
//			}
//			
//		});
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

}
