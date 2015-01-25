package com.ggj2015.whatnow.states.world.entities.systems;

import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.systems.GroupSystem;
import com.lostcode.javalib.utils.Random;

public class AnimalSystem extends GroupSystem {
	static Random r = new Random();
	
	public AnimalSystem() {
		super("Animals");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void process(Entity e) {
		if(r.nextDouble(1) <0.02){
			((Body)e.getComponent(Body.class)).setLinearVelocity(r.nextVector2());
		}
		
	}

}
