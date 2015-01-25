/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities.templates;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.ggj2015.whatnow.states.world.entities.components.AnimatedSprite;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.ComponentManager;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.physical.Collidable;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.Convert;

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

		
		AnimatedSprite as = AnimatedSprite.newSprite((NowWorld)world, "wizard", "wizard", "wizard");
		as.setScale(0.1f, 0.1f);
		as.setLayer(layer);
		e.addComponent(as);
		
		BodyDef bd = new BodyDef();
		bd.position.set(pos.x,pos.y);
		bd.type = BodyType.DynamicBody;
		bd.allowSleep = false;
		bd.fixedRotation = true;
		
		
		
		FixtureDef fd = new FixtureDef();
		fd.shape = new CircleShape();
		fd.shape.setRadius(Convert.pixelsToMeters(as.getWidth()/2f));
		
		e.addComponent(new Body(world,e,bd,fd));
		
		e.addComponent(new Collidable(){

			@Override
			public void onAdd(ComponentManager container) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRemove(ComponentManager container) {
				// TODO Auto-generated method stub
				
			}});

		return e;
	}

}
