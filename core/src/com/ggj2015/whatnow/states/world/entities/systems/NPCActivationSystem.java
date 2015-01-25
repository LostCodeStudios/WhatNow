package com.ggj2015.whatnow.states.world.entities.systems;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.systems.InputSystem;

public class NPCActivationSystem extends InputSystem {

	public Entity touchingEntity = null;
	
	public NPCActivationSystem(InputMultiplexer input) {
		super(input);
	}

	@Override
	protected void process(Entity e) {
		
	}

	@Override
	public boolean canProcess(Entity e) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			if (touchingEntity != null) {
				// TODO activate the entity the player is touching
				
				return true;
			}
		}

		return false;
	}
	
}
