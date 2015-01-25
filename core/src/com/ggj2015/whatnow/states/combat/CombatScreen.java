package com.ggj2015.whatnow.states.combat;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ggj2015.whatnow.states.dialog.DialogScreen;
import com.lostcode.javalib.Game;

public class CombatScreen extends DialogScreen {

	enum CombatState {
		PlayerChoice,
		PlayerAction,
		EnemyAction
	}
	
	String playerChoice = "";
	
	public void setPlayerChoice(String choice) {
		playerChoice = choice;
	}
	
	CombatState state = CombatState.PlayerChoice;
	
	Texture battlefieldTexture;
	
	private String enemyName;
	private boolean enemyInvincible;
	private int enemyHealth;
	private static Random RAND = new Random();
	
	public CombatScreen(Game game, SpriteBatch spriteBatch, String enemyName, boolean enemyInvincible, int enemyHealth) {
		super(game, spriteBatch);
		
		battlefieldTexture = new Texture(Gdx.files.internal("sprites/battlefield.png"));
		
		this.enemyName = enemyName;
		this.enemyInvincible = enemyInvincible;
		this.enemyHealth = enemyHealth; 
		
		startState(CombatState.PlayerChoice);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		battlefieldTexture.dispose();
	}
	
	void startState(CombatState newState) {
		state = newState;
		
		switch (state) {
		case PlayerChoice:
			this.showDialog(new CombatDialog(this, enemyName, enemyInvincible));
			break;
		case PlayerAction:
			this.showDialog(new ActionDialog(this, playerChoice, CombatState.EnemyAction));
			break;
		case EnemyAction:
			String toPrint = getEnemyText();
			this.showDialog(new ActionDialog(this, toPrint, CombatState.PlayerChoice));
			break;
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

	@Override
	public void render(float delta) {
		renderBackground();
		
		Vector2 playerFieldPos = new Vector2(128, 50); // TODO adjust this
		Vector2 enemyFieldPos = new Vector2(1280 - 128 - 580, 720 - 300); // TODO adjust this
		renderBattlefield(playerFieldPos);
		renderBattlefield(enemyFieldPos);
		
		// TODO render characters
		// TODO render health bars
		
		super.render(delta); // render dialogs last
	}
	
	void renderBackground() {
		// TODO render fullscreen background
	}
	
	void renderBattlefield(Vector2 position) {
		float fieldScale = 1f; // TODO adjust this
		
		spriteBatch.begin();
		
		spriteBatch.draw(battlefieldTexture, position.x, position.y, battlefieldTexture.getWidth() * fieldScale, battlefieldTexture.getHeight() * fieldScale);
		
		spriteBatch.end();
	}
	
	public void damageEnemy(int amount) {
		enemyHealth -= amount;
		if (enemyHealth < 0)
			return;
		//TODO Add results of killing enemy
	}
	
	public void useItem(String type, int amount) {
		if (type.equals("damage"))
			damageEnemy(amount);
	}

}
