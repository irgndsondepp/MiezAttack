package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.ShotgunProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * the shotgun class extending gun
 * 
 * @author Robert
 *
 */
public class Shotgun extends Gun {

	/**
	 * create a shotgun at (x,y) in the level. a shotgun has specific values and
	 * a sprite given to the gun super class constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public Shotgun(int x, int y) {
		super(x, y, 7, 50, 70, 12, Sprite.shotgun);
	}

	/**
	 * override the shoot method of the super class to spawn three shotgun
	 * projectiles with slightly different angles (spread shot) and play a
	 * shotgun sound.
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new ShotgunProjectile(x, y, damage, speed, dir));
		level.add(new ShotgunProjectile(x, y, damage, speed, dir - 0.2));
		level.add(new ShotgunProjectile(x, y, damage, speed, dir + 0.2));
		Sound.shotgun_shot.playSound();
		shotsLeft--;
		return shotsLeft;

	}

}
