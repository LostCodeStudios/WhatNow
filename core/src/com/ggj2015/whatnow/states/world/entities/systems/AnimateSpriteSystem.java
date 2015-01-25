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
		float h2 = as.getBase().getHeight() / 2, offset = 30f;

		if (as.animating) {
			as.elapsedSec += deltaSeconds();

			float s1 =
					(float) (Math.sin(as.elapsedSec * speed)
							* as.getBase().getWidth() / 3);
			float s2 =
					(float) (Math.sin((as.elapsedSec * speed) + Math.PI)
							* as.getBase().getWidth() / 3);
			float s3 =
					(float) (Math.sin((as.elapsedSec * speed) + Math.PI + 0.5)
							* as.getBase().getWidth() / 2);
			float s4 =
					(float) (Math.sin((as.elapsedSec * speed) - 0.5)
							* as.getBase().getWidth() / 2);

			as.lFootSprite.setOrigin(new Vector2(s1 + offset, -h2 / 2));
			as.rFootSprite.setOrigin(new Vector2(s2 + offset, h2 / 2));
			as.lHandSprite.setOrigin(new Vector2(s3, -h2));
			as.rHandSprite.setOrigin(new Vector2(s4, h2));
		}
		else {
			as.lFootSprite.setOrigin(new Vector2(+offset, -h2 / 2));
			as.rFootSprite.setOrigin(new Vector2(+offset, h2 / 2));
			as.lHandSprite.setOrigin(new Vector2(0, -h2));
			as.rHandSprite.setOrigin(new Vector2(0, h2));
		}

		as.rotationTo = (float) Math.toRadians(b.getLinearVelocity().angle());

		while (as.rotationTo - as.rotation > Math.PI)
			as.rotationTo -= 2 * Math.PI;
		while (as.rotation - as.rotationTo > Math.PI)
			as.rotationTo += 2 * Math.PI;

		as.rotation += (as.rotationTo - as.rotation) * 0.3f;
		System.out.println(as.rotation);
		b.setRotation(as.rotation);
	}
}
