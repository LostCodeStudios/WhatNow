package com.ggj2015.whatnow.states.world.entities.systems;

import com.badlogic.gdx.utils.Array;
import com.ggj2015.whatnow.states.world.entities.components.AnimatedSprite;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.systems.ComponentSystem;

public class AnimateSpriteSystem extends ComponentSystem {

	@SuppressWarnings("unchecked")
	public AnimateSpriteSystem() {
		super(AnimatedSprite.class, Body.class);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	protected void process(Entity e) {
		AnimatedSprite as = (AnimatedSprite) e.getComponent(AnimatedSprite.class);
		
		as.clearChildren();
		
		Body b = (Body) e.getComponent(Body.class);
		
		as.animating = b.getLinearVelocity().len() > 0.05f;
		
		if (as.animating) {
			as.elapsedSec += deltaSeconds();
			
			if (as.elapsedSec > as.frameTime) {
				as.elapsedSec -= as.frameTime;
				
				as.frame = !as.frame;
			}
			
			as.addChild(as.movingFootSprites.get(as.frame));
			as.addChild(as.movingHandSprites.get(as.frame));
		} else {
			as.addChild(as.footSprite);
			as.addChild(as.handSprite);
		}
		
		as.reorder(new Array<Integer>(new Integer[] { 1, 2, 0 }));
		
		as.setRotation((float)Math.toDegrees(b.getRotation()));
	}

}
