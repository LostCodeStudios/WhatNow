package com.ggj2015.whatnow.states.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;
import com.lostcode.javalib.entities.EntityWorld;

public class NowWorld extends EntityWorld {

	private Rectangle bounds;
	
	public NowWorld(InputMultiplexer input, Camera camera, String levelData) {
		super(input, camera, Vector2.Zero.cpy());
		buildLevel(levelData);
		
	}

	
	protected void buildLevel(String levelDataFile){
		Json jsonParser = new Json();
		Level level = jsonParser.fromJson(Level.class, Gdx.files.internal(levelDataFile));
		
		//Establish basic world charactersitics.
		this.camera.position.set(new Vector3(level.getCameraInitial(),0));
		this.physicsWorld.setGravity(level.getGravity());
		this.bounds = level.getBounds();
		this.spriteSheet = level.getSpriteSheet();
		
		//Initialize the game objects or entities :)
		for(GameObject obj : level.getGameObjects()){
			
		}
		
		
		
		
	}
	
	@Override
	protected void buildSystems() {
		super.buildSystems();
		
		debugView.enabled = true;
	}



	@Override
	protected void buildTemplates() {
		super.buildTemplates();
	}



	@Override
	protected void buildEntities() {
		super.buildEntities();
	}



	@Override
	protected void buildSpriteSheet() {
		
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

}
