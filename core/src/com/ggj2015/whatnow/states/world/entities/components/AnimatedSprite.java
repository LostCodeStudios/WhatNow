package com.ggj2015.whatnow.states.world.entities.components;

import com.badlogic.gdx.utils.Array;
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

	public float rotation = 0, rotationTo = 0;

	public Sprite lHandSprite, rHandSprite;
	public Sprite lFootSprite, rFootSprite;

	public static AnimatedSprite newSprite(NowWorld world, String feetPrefix,
			String handPrefix, String bodyKey) {
		String feetKeyStub = feetPrefix;
		if (!feetPrefix.isEmpty())
			feetKeyStub += "-";
		feetKeyStub += "feet"; // now requires frame #

		String handKeyStub = handPrefix;
		if (!handPrefix.isEmpty())
			handKeyStub += "-";
		handKeyStub += "hands";

		Sprite body = new Sprite(world.getSpriteSheet(), bodyKey);

		AnimatedSprite as = new AnimatedSprite(body); // TODO'

		as.lHandSprite = new Sprite(world.getSpriteSheet(), handKeyStub + "L");
		as.rHandSprite = new Sprite(world.getSpriteSheet(), handKeyStub + "R");

		as.lFootSprite = new Sprite(world.getSpriteSheet(), feetKeyStub + "L");
		as.rFootSprite = new Sprite(world.getSpriteSheet(), feetKeyStub + "R");

		as.addChild(as.lHandSprite);
		as.addChild(as.rHandSprite);
		as.addChild(as.lFootSprite);
		as.addChild(as.rFootSprite);

		as.reorder(new Array<Integer>(new Integer[] { 3, 4, 1, 2, 0 }));

		return as;
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);

		lHandSprite.setScale(scaleX, scaleY);
		rHandSprite.setScale(scaleX, scaleY);
		rFootSprite.setScale(scaleX, scaleY);
		lFootSprite.setScale(scaleX, scaleY);

	}
}
