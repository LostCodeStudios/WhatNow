package com.ggj2015.whatnow.states.world.entities.components;

import com.badlogic.gdx.utils.ObjectMap;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.lostcode.javalib.entities.components.render.MultiRenderable;
import com.lostcode.javalib.entities.components.render.Renderable;
import com.lostcode.javalib.entities.components.render.Sprite;

// a really hacky class to manage moving character sprites
public class AnimatedSprite extends MultiRenderable {
	
	public AnimatedSprite(Renderable base) {
		super(base);
	}
	
	public float elapsedSec = 0f;
	public boolean animating = false;
	
	public Sprite handSprite;
	public Sprite footSprite;
	
	public static AnimatedSprite newSprite(NowWorld world, String feetPrefix, String handPrefix, String bodyKey) {
		String feetKeyStub = feetPrefix;
		if (!feetPrefix.isEmpty()) feetKeyStub += "-";
		feetKeyStub += "feet"; // now requires frame #
		
		String handKeyStub = handPrefix;
		if (!handPrefix.isEmpty()) handKeyStub += "-";
		handKeyStub += "hands";
		
		Sprite body = new Sprite(world.bodiesSheet, bodyKey);
		
		AnimatedSprite as = new AnimatedSprite(body); // TODO
		
		as.handSprite = new Sprite(world.handsSheet, handKeyStub + "1");
		as.footSprite = new Sprite(world.feetSheet, feetKeyStub + "1");
		
		as.movingHandSprites.put(false, new Sprite(world.handsSheet, handKeyStub + "2"));
		as.movingHandSprites.put(true, new Sprite(world.handsSheet, handKeyStub + "3"));
		as.movingFootSprites.put(false, new Sprite(world.feetSheet, feetKeyStub + "2"));
		as.movingFootSprites.put(true, new Sprite(world.feetSheet, feetKeyStub + "3"));
		
		return as;
	}
	
}
