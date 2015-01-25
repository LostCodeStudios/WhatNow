/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities.templates;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.physical.Sensor;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.Convert;

/**
 * @author MadcowD
 *
 */
public class PropTemplate implements EntityTemplate {

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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

		System.out.println(pos);
		System.out.println(spriteKey);
		
		Sprite spr = (Sprite) e.addComponent(new Sprite(world.getSpriteSheet(),spriteKey,scale,layer));

		
		 //PHYSICAL STUIFF
		BodyDef bd = new BodyDef();
		bd.position.set(pos.x,pos.y);
		bd.type = BodyType.StaticBody;
		bd.allowSleep = false;
		bd.fixedRotation = true;

		FixtureDef fd = new FixtureDef();
		fd.isSensor = true;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Convert.pixelsToMeters(spr.getHeight()/2f), 
				Convert.pixelsToMeters(spr.getWidth()/2f));
		fd.shape = shape;
		e.addComponent(new Body(world,e,bd,fd));
		
		//COLLISION CODE OR TELEPORTATION.
		final NowWorld nw = (NowWorld)world;
		e.addComponent(new Sensor(e));

		return e;
	}

}
