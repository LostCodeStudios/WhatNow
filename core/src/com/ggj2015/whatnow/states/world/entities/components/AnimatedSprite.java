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
	public float frameTime = 0.75f;
	public boolean animating = false;
	public boolean frame = false; // alternate this
	
	public Sprite handSprite;
	public Sprite footSprite;
	public ObjectMap<Boolean, Sprite> movingHandSprites = new ObjectMap<Boolean, Sprite>();
	public ObjectMap<Boolean, Sprite> movingFootSprites = new ObjectMap<Boolean, Sprite>();
	
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

	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
		
		handSprite.setScale(scaleX, scaleY);
		footSprite.setScale(scaleX, scaleY);
		movingHandSprites.get(false).setScale(scaleX, scaleY);
		movingHandSprites.get(true).setScale(scaleX, scaleY);
		movingHandSprites.get(false).setScale(scaleX, scaleY);
		movingHandSprites.get(true).setScale(scaleX, scaleY);
	}
	
}
