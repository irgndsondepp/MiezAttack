package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.RocketProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * class rocketshotgun
 * 
 * @author Robert
 *
 */
public class RocketShotgun extends Gun {

	/**
	 * creates a rocketshotgun at x and y. a rocket shotgun has a specific
	 * sprite and specific values given to the gun super class constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public RocketShotgun(int x, int y) {
		super(x, y, 7, 100, 120, 10, Sprite.rocket_shotgun);
	}

	/**
	 * override the shoot method of the superclass, spawning three rocket
	 * projectiles with slightly different angles (spread shot) and playing the
	 * shotgun sound
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new RocketProjectile(x, y, damage, speed, dir));
		level.add(new RocketProjectile(x, y, damage, speed, dir - 0.2));
		level.add(new RocketProjectile(x, y, damage, speed, dir + 0.2));
		Sound.shotgun_shot.playSound();
		shotsLeft--;
		return shotsLeft;

	}
}
