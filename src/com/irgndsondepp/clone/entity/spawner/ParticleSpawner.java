package com.irgndsondepp.clone.entity.spawner;

import com.irgndsondepp.clone.entity.particle.Particle;
import com.irgndsondepp.clone.level.Level;

/**
 * particle spawner class extending a spawner. the particle spawner creates an
 * amount of particles and adds them to the level.
 * 
 * @author Robert
 *
 */
public class ParticleSpawner extends Spawner {

	/**
	 * create a particle spawner
	 * 
	 * @param x
	 *            x position in the level
	 * @param y
	 *            y position in the level
	 * @param life
	 *            the life time of the created particles
	 * @param amount
	 *            the amount of particles
	 * @param level
	 *            the level
	 */
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life));
		}
	}

	/**
	 * create a particle spawner spawning particles with a specific color. will
	 * vary the color slightly with a random value.
	 * 
	 * @param x
	 *            x position in the level
	 * @param y
	 *            y position in the level
	 * @param life
	 *            the life time of the created particles
	 * @param amount
	 *            the amount of particles
	 * @param col
	 *            the color of the particles
	 * @param level
	 *            the level
	 */
	public ParticleSpawner(int x, int y, int life, int amount, int col,
			Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life, col + random.nextInt(150)));
		}
	}

}
