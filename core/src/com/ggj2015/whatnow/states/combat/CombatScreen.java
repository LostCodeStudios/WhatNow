package com.ggj2015.whatnow.states.combat;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.utils.SpriteSheet;

public class CombatScreen extends DialogScreen {

	enum CombatState {
		PlayerChoice,
		PlayerAction,
		EnemyAction,
		Over
	}
	
	String playerChoice = "";
	
	ObjectMap<String, Float> enemyScales = new ObjectMap<String, Float>();
	
	public void setPlayerChoice(String choice) {
		playerChoice = choice;
	}
	
	CombatState state = CombatState.PlayerChoice;
	
	Texture battlefieldTexture;
	
	private String enemyName;
	private boolean enemyInvincible;
	private boolean fullParty;
	
	private SpriteSheet sheet;
	
	public CombatScreen(Game game, SpriteBatch spriteBatch, String enemyName, boolean enemyInvincible,
			boolean fullParty) {

		super(game, spriteBatch);

		enemyScales.put("dragon-in-battle", 0.25f);
		
		battlefieldTexture = new Texture(Gdx.files.internal("sprites/battlefield.png"));

		this.enemyName = enemyName;
		this.enemyInvincible = enemyInvincible;
		this.fullParty = fullParty;
		
		try {
			sheet = SpriteSheet.fromXML(Gdx.files.internal("spritesheet.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		startState(CombatState.PlayerChoice);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		battlefieldTexture.dispose();
		sheet.dispose();
	}
	
	void startState(CombatState newState) {
		state = newState;
		
		switch (state) {
		case PlayerChoice:
		this.showDialog(new CombatDialog(this, enemyName, enemyInvincible, !enemyName.equals("dragon-in-battle")));
			break;
		case PlayerAction:
			this.showDialog(new ActionDialog(this, playerChoice, CombatState.EnemyAction));
			break;
		case EnemyAction:
			this.showDialog(new ActionDialog(this, "The enemy fights back!", CombatState.PlayerChoice));
			break;
		case Over:
			getGame().getScreenManager().closeActiveScreen(); // close this
			
			if (enemyName.equals("dragon-in-battle")) {
				// start cave
				getGame().getScreenManager().addScreen(new WorldScreen(getGame(), spriteBatch, "overworld.lol"));
			}
		}
	}
	Vector2 playerFieldPos = new Vector2(128, -50); // TODO adjust this
	Vector2 enemyFieldPos = new Vector2(1280 - 128 - 580, 720 - 300); // TODO adjust this
	@Override
	public void render(float delta) {
		renderBackground();
		
		
		renderBattlefield(playerFieldPos);
		renderBattlefield(enemyFieldPos);
		
		renderParty();
		renderEnemy();
		
		super.render(delta); // render dialogs last
	}
	
	void renderBackground() {
		// TODO render fullscreen background
	}
	
	void renderParty() {
		spriteBatch.begin();
		if (fullParty) {
			spriteBatch.draw(sheet.getRegion("fighter-young"), 0, 0);
			spriteBatch.draw(sheet.getRegion("priest"), 150, 0);
			spriteBatch.draw(sheet.getRegion("wizard"), 340, 0);
			spriteBatch.draw(sheet.getRegion("thief"), 450, 0);
		} else {
			spriteBatch.draw(sheet.getRegion("fighter-old"), 0, 0);
		}
		spriteBatch.end();
	}
	
	void renderEnemy() {
		spriteBatch.begin();
		TextureRegion reg = sheet.getRegion(enemyName);

		
		float scale = 1f;
		if (enemyScales.containsKey(enemyName)) {
			scale = enemyScales.get(enemyName);
		}
		
		float w = reg.getRegionWidth();
		float h = reg.getRegionHeight();
		
		spriteBatch.draw(reg, enemyFieldPos.x, enemyFieldPos.y, 0, 0, w, h, scale, scale, 0f);
		spriteBatch.end();
	}
	
	void renderBattlefield(Vector2 position) {
		float fieldScale = 1f; // TODO adjust this
		
		spriteBatch.begin();
		
		spriteBatch.draw(battlefieldTexture, position.x, position.y, battlefieldTexture.getWidth() * fieldScale, battlefieldTexture.getHeight() * fieldScale);
		
		spriteBatch.end();
	}
	
	private int enemyHealth = 5000;
	
	public void damageEnemy(int amount) {
		if (!enemyInvincible) {
			enemyHealth -= amount;
		}
		
		if (enemyHealth < 0) {
			showDialog(new ActionDialog(this, "You have defeated the enemy!", CombatState.Over));
		}
	}
	
	public void useItem(String type, int amount) {
		if (type.equals("damage"))
			damageEnemy(amount);
	}

}
