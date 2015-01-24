package com.ggj2015.whatnow.states.world.entities;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.EntityWorld;

public class NowWorld extends EntityWorld {

	private Rectangle bounds;
	
	public NowWorld(InputMultiplexer input, Camera camera) {
		
		super(input, camera, Vector2.Zero.cpy());
		
		// TODO form proper bounds around map
		bounds = new Rectangle(-20, -12, 40, 24); // close to screen size
	}

	
	protected void buildLevelData(){
		
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
