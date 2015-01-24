package com.ggj2015.whatnow.states.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ggj2015.whatnow.states.DialogScreen;
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
	
	public CombatScreen(Game game, SpriteBatch spriteBatch) {
		super(game, spriteBatch);
		
		battlefieldTexture = new Texture(Gdx.files.internal("sprites/battlefield.png"));
		
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
			this.showDialog(new CombatDialog(this));
			break;
		case PlayerAction:
			// TODO carry out the player's choice
			break;
		case EnemyAction:
			// TODO carry out the enemy turn
			break;
		}
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
		// TODO render animation effects
		
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

}
