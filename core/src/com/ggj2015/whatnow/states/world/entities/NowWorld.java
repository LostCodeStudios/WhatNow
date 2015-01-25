package com.ggj2015.whatnow.states.world.entities;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.entities.templates.PlayerTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.TileTemplate;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.utils.SpriteSheet;

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
		try {
			this.spriteSheet = SpriteSheet.fromXML(Gdx.files.internal(level.getSpriteSheetFile()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Initialize the game objects or entities :)
		for(GameObject obj : level.getGameObjects()){
			Entity e = this.createEntity(obj.getTemplate(),
					 obj.getPosition(), obj.getSpriteKey(), obj.getScale(), obj.getLayer());
			e.init(obj.getTag(), obj.getGroup(),obj.getType());
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

		this.addTemplate("Player", new PlayerTemplate());

		this.addTemplate("Tile", new TileTemplate());
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
