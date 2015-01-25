/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities.templates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.ggj2015.whatnow.states.world.entities.components.AnimatedSprite;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.ComponentManager;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.physical.Collidable;
import com.lostcode.javalib.entities.components.physical.Particle;
import com.lostcode.javalib.entities.components.physical.Sensor;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.Convert;

public class NPCTemplate implements EntityTemplate {

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		
	}

	/* (non-Javadoc)
	 * @see com.lostcode.javalib.entities.templates.EntityTemplate#buildEntity(com.lostcode.javalib.entities.Entity, com.lostcode.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@Override
	public Entity buildEntity(final Entity e, EntityWorld world, Object... args) {
		Vector3 pos = (Vector3)args[0];
		String spriteKey = (String)args[1];
		float scale = (Float)args[2];
		int layer = (Integer)args[3];

		
		AnimatedSprite s = AnimatedSprite.newSprite((NowWorld)world, "", "", "generic");
		s.setScale(scale, scale);
		s.setLayer(layer);
		e.addComponent(s);
		
		Particle particle = new Particle(e, new Vector2(pos.x, pos.y), 0f);
		e.addComponent(particle);

		BodyDef bd = new BodyDef();
		bd.position.set(pos.x,pos.y);
		bd.type = BodyType.DynamicBody;
		bd.allowSleep = false;
		bd.fixedRotation = true;
		
		
		
		FixtureDef fd = new FixtureDef();
		fd.shape = new CircleShape();
		fd.shape.setRadius(Convert.pixelsToMeters(s.getWidth()/2f));
		fd.isSensor = true;
		
		e.addComponent(new Body(world,e,bd,fd));
		
		e.addComponent(new Sensor(e) {

			@Override
			public void onDetected(Entity e1, EntityWorld world) {
				NowWorld nw = (NowWorld) world;
				
				nw.npcActivationSystem.touchingEntity = e;
			}

			@Override
			public void onEscaped(Entity e1, EntityWorld world) {
				NowWorld nw = (NowWorld) world;
				
				nw.npcActivationSystem.touchingEntity = null;
			}
			
		});
		
		return e;
	}

}

