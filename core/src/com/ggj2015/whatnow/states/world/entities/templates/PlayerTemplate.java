/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities.templates;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.templates.EntityTemplate;

/**
 * @author MadcowD
 * The general player template.
 * https://gist.github.com/MadcowD/469ba7cf50d178100d2c
 */
public class PlayerTemplate implements EntityTemplate {

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
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		Vector3 pos = (Vector3)args[0];
		String spriteKey = (String)args[1];
		float scale = (Float)args[2];
		int layer = (Integer)args[3];

		Sprite spr = (Sprite) e.addComponent(new Sprite(world.getSpriteSheet(),spriteKey,scale,layer));

		BodyDef bd = new BodyDef();
		bd.position.set(pos.x,pos.y);
		bd.type = BodyType.DynamicBody;
		bd.allowSleep = false;
		bd.fixedRotation = true;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = new CircleShape();
		fd.shape.setRadius(spr.getWidth()/2f);
		
		e.addComponent(new Body(world,e,bd,fd));
		
		

		return null;
	}

}
