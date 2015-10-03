package com.irgndsondepp.clone.entity.spawner;

import com.irgndsondepp.clone.entity.Entity;
import com.irgndsondepp.clone.level.Level;

/**
 * the spawner class able to spawn particles and mobs. at the moment the
 * particle spawn logic is handled by the according subclass
 * 
 * @author Robert
 *
 */
public class Spawner extends Entity {

	public enum Type {
		MOB, PARTICLE;
	}

	// private Type type;

	/**
	 * the constructor spawns the entities given by type
	 * 
	 * @param x
	 *            the x postion where stuff will spawn
	 * @param y
	 *            the y position
	 * @param type
	 *            the type of stuff to be spawned
	 * @param amount
	 *            the amount of stuff
	 * @param level
	 *            the level
	 */
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		// this.type = type;
		for (int i = 0; i < amount; i++) {

		}
		// add this to the level to remove it with the next update
		level.add(this);
		this.remove();
	}

}
