package com.ggj2015.whatnow.states.world.entities.systems;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.ggj2015.whatnow.states.world.entities.Player;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.systems.InputSystem;

public class PlayerControlSystem extends InputSystem {	
	HashMap<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>();
	
	public PlayerControlSystem(InputMultiplexer input) {
		super(input);
		keyMap.put(Keys.W, false);
		keyMap.put(Keys.A, false);
		keyMap.put(Keys.S, false);
		keyMap.put(Keys.D, false);
	}
	
	@Override
	protected void process(Entity e) {
		Body bd = e.getComponent(Body.class);
		if(bd != null){
			//MOVEMENT
			Vector2 vel = new Vector2(0,0);
			boolean movementFlag = false;
			if(keyMap.get(Keys.W)== true) {
				vel.add(0, 1);
				movementFlag = true;
			}
			if(keyMap.get(Keys.A) == true) {
				vel.add(-1, 0);
				movementFlag = true;
			}
			if(keyMap.get(Keys.S) 	 == true) {
				vel.add(0, -1);
				movementFlag = true;
			}
			if(keyMap.get(Keys.D) == true) {
				vel.add(1, 0);
				movementFlag = true;
			}
			
			if (movementFlag /*&& !World.equals(walk_back)*/) {
				//Scale player hunger and thirst
				if (Player.HUNGER < 99 )
					Player.HUNGER += (.01 / (100 - Player.HUNGER));
				if (Player.THIRST < 99)
					Player.THIRST += (.01 / (100 - Player.THIRST));
				
				//TODO add hunger/thirst encounters
			}
			
			vel.scl(5);
			bd.setLinearVelocity(vel);
			
//			if (vel.len() > 0.05f)
//				bd.setRotation((float)Math.toRadians(vel.angle()));
		}
		//TODO: Interaction section
		
		
		super.process(e);
	}

	@Override
	public boolean canProcess(Entity e) {
		if(e.getTag().equalsIgnoreCase("player"))
			return true;
		else
			return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT)
			world.setTimeCoefficient(2);
			//TODO: SOUND SPEED UP
		
		keyMap.put(keycode, true);
		
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT)
			world.setTimeCoefficient(1);
			//TODO: Sound effect

		keyMap.put(keycode, false);
		return false;
	}
}
