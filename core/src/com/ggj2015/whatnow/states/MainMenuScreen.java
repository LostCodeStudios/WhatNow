package com.ggj2015.whatnow.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.combat.CombatScreen;
import com.ggj2015.whatnow.states.dialog.DialogMenu;
import com.ggj2015.whatnow.states.dialog.DialogNode;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.dialog.DialogStyle;
import com.lostcode.javalib.Game;

public class MainMenuScreen extends DialogScreen {
	SpriteBatch sb = new SpriteBatch();
	Texture bg;
	public MainMenuScreen(Game game, final SpriteBatch spriteBatch) {
		super(game, spriteBatch);
		bg = new Texture(Gdx.files.internal("bgfucker.png"));
		
		// TODO polish: add sprites, large title, stylization etc.
		
		Array<String> text = new Array<String>();
		text.add("Lingering");
		
		Array<String> options = new Array<String>();
		options.add("New Game");
		options.add("Load Last Save");
//		options.add("Test fight"); // TODO remove this
		//options.add("Options");
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
		
		DialogStyle d = DialogStyle.DEFAULT.cpy();
		d.bounds.y =400/8f;
		
		showDialog(new DialogMenu(d, menuNode) {

			@Override
			public void onDialogChoice(String choice) {
				if (choice.equals("Load Last Save")) {
					MainMenuScreen.this.game.getScreenManager().addScreen(
							new CombatScreen(
									MainMenuScreen.this.game, 
									MainMenuScreen.this.spriteBatch, "dragon-in-battle", false, true, 5000));
				}
				else if (choice.equals("Test fight")) {
					MainMenuScreen.this.game.getScreenManager().addScreen(new CombatScreen(MainMenuScreen.this.game, MainMenuScreen.this.spriteBatch, "Thirst", true, false, 500));
				}
				else if (choice.equals("Options")) {
					// TODO make an options screen
				}
				else if (choice.equals("Quit")) {
					Gdx.app.exit();
				}
			}
			
		});
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
		sb.begin();
		sb.draw(bg, 0,0, 1280,720);
		sb.end();
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
