package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.RocketProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * the rocketlauncher class extending gun
 * 
 * @author Robert
 *
 */
public class RocketLauncher extends Gun {

	/**
	 * create a rocket launcher at (x,y) in the level. a rocket launcher has a
	 * specific sprite and specific values, which are used with the gun super
	 * class constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public RocketLauncher(int x, int y) {
		super(x, y, 5, 100, 120, 3, Sprite.rocket_launcher);

	}

	/**
	 * spawns a rocket projectile instead of a gun projectile and plays the
	 * fireball sound.
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new RocketProjectile(x, y, damage, speed, dir));
		Sound.fireball.playSound();
		shotsLeft--;
		return shotsLeft;

	}

}
