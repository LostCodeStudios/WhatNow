package com.ggj2015.whatnow.states.world.entities.systems;

import com.badlogic.gdx.math.Vector2;
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
		AnimatedSprite as =
				(AnimatedSprite) e.getComponent(AnimatedSprite.class);

		Body b = (Body) e.getComponent(Body.class);

		as.animating = b.getLinearVelocity().len() > 0.05f;
		float speed = b.getLinearVelocity().len() * 1.2f;
		float h2 = as.getBase().getWidth() / 2.2f, offsetx = 30f, offsety = 25f, fff = 20f;

		if (as.animating) {
			as.elapsedSec += deltaSeconds();
			float s1 =
					(float) (Math.sin(as.elapsedSec * speed)
							* as.getBase().getHeight() / 3);
			float s2 =
					(float) (Math.sin((as.elapsedSec * speed) + Math.PI)
							* as.getBase().getHeight() / 3);
			float s3 =
					(float) (Math.sin((as.elapsedSec * speed) + Math.PI + 0.5)
							* as.getBase().getHeight() / 2);
			float s4 =
					(float) (Math.sin((as.elapsedSec * speed) - 0.5)
							* as.getBase().getHeight() / 2);

			as.lFootSprite.setOrigin(new Vector2(h2 / 2+offsety, s1 + offsetx));
			as.rFootSprite.setOrigin(new Vector2(- h2 / 2+offsety, s2 + offsetx));
			as.lHandSprite.setOrigin(new Vector2(h2+fff,s3 ));
			as.rHandSprite.setOrigin(new Vector2(-h2+fff, s4 ));

			as.rotationTo =
					(float)( Math.toRadians(b.getLinearVelocity().angle()) - Math.PI/2.0f);

		}
		else {
			as.lFootSprite.setOrigin(new Vector2( h2 / 2+offsety, +offsetx));
			as.rFootSprite.setOrigin(new Vector2( -h2 / 2+offsety, +offsetx));
			as.lHandSprite.setOrigin(new Vector2( h2+fff,0));
			as.rHandSprite.setOrigin(new Vector2( -h2+fff,0));
		}

		while (as.rotationTo - as.rotation > Math.PI)
			as.rotationTo -= 2 * Math.PI;
		while (as.rotation - as.rotationTo > Math.PI)
			as.rotationTo += 2 * Math.PI;

		as.rotation += (as.rotationTo - as.rotation) * 0.3f;
		b.setRotation(as.rotation);
	}
}
