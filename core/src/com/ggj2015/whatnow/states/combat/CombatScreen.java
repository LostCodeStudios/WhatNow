package com.ggj2015.whatnow.states.combat;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.ggj2015.whatnow.states.world.entities.Player;
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
	private static Random RAND = new Random();
	private boolean fullParty;
	private int enemyHealth;
	
	private SpriteSheet sheet;
	
	public CombatScreen(Game game, SpriteBatch spriteBatch, String enemyName, boolean enemyInvincible,
			boolean fullParty, int enemyHealth) {

		super(game, spriteBatch);

		enemyScales.put("dragon-in-battle", 0.25f);
		
		battlefieldTexture = new Texture(Gdx.files.internal("sprites/battlefield.png"));
		
		this.enemyName = enemyName;
		this.enemyInvincible = enemyInvincible;
		this.enemyHealth = enemyHealth; 
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
			String toPrint = getEnemyText();
			this.showDialog(new ActionDialog(this, toPrint, CombatState.PlayerChoice));
			break;
		case Over:
			getGame().getScreenManager().closeActiveScreen(); // close this
			
			if (enemyName.equals("dragon-in-battle")) {
				// start cave
				getGame().getScreenManager().addScreen(new WorldScreen(getGame(), spriteBatch, "overworld.lol"));
			}
		}
	}
	
	private String getEnemyText() {
		if (enemyName.equals("Farm"))
			return "The farm stares blankly at you!";
		if (enemyName.equals("Dust"))
			return "You inhale all the dust swirling around you, causing you to take " + RAND.nextInt(20) + " damage!";
		if (enemyName.equals("Hunger"))
			return "Your stomach growls hungrily, inflicting " + (RAND.nextInt(40) + 10) + " damage!";
		if (enemyName.equals("Thirst"))
			return "Your mouth is so dry, you take " + (RAND.nextInt(40) + 10) + " damage!";
		if (enemyName.equals("Well"))
			return "The well continues to squat on the ground!";
		if (enemyName.equals("Winch-less Well"))
			return "The well continues to squat on the ground (without a winch)!";
		if (enemyName.equals("Slime"))
			return "The slime dances happily, not bothering you!";
		if (enemyName.equals("Dragon"))
			return "The Dragon blasts you with his most powerful attack, dealing an incredible " + (RAND.nextInt(500) + 500) + " damage!";
		return "Nothing interesting happens!"; //Catch-all
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
	
	public void damageEnemy(int amount) {
		enemyHealth -= amount;
		
		if (enemyHealth < 0) {
			if (enemyName.equals("Well"))
				Player.addItem("Filled Bucket");
			if (enemyName.equals("Farm") && enemyHealth > -4000)
				Player.addItem("Food");			
			
			showDialog(new ActionDialog(this, "You have defeated the enemy!", CombatState.Over));
		}
	}
	
	public void useItem(String type, int amount) {
		if (type.equals("damage"))
			damageEnemy(amount);
	}

}
