/**
 * 
 */
package com.ggj2015.whatnow.states.world.entities.systems;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.ggj2015.whatnow.states.combat.CombatScreen;
import com.ggj2015.whatnow.states.world.WorldScreen;
import com.ggj2015.whatnow.states.world.entities.NowWorld;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.physical.Sensor;
import com.lostcode.javalib.entities.systems.InputSystem;

/**
 * @author MadcowD
 *
 */
public class PropActivationSystem extends InputSystem {

	/**
	 * @param input
	 */
	public PropActivationSystem(InputMultiplexer input) {
		super(input);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canProcess(Entity e) {
		if(e.getGroup().equalsIgnoreCase("Props"))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE){
			for(int i = 0; i < this.entities.size; i++)
			{
				Entity e = entities.get(i);
				if(e != null){
					Sensor s = (Sensor) e.getComponent(Sensor.class);
					if(s.contains(e)) {
						WorldScreen screen = ((NowWorld) world).getMyScreen();
						screen.screenManager().addScreen(
								new CombatScreen(screen.getGame(), screen.getSpriteBatch(), e.getTag(), false, false, 400));

						return false;
					}
				}
			}
		}
		return false;
	}

}
