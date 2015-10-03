package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.GunProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * create a handgun as a subclass of gun
 * 
 * @author Robert
 *
 */
public class HandGun extends Gun {

	/**
	 * create a handgun at (x,y) in the level. a handgun has a specific sprite
	 * and specific values for the gun super class constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public HandGun(int x, int y) {
		super(x, y, 15, 30, 20, 12, Sprite.handgun);
		// TODO Auto-generated constructor stub
	}

	/**
	 * override the shoot method of the super class spawning a gun projectile
	 * and playing different handgun sounds
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new GunProjectile(x, y, damage, speed, dir));
		int n = random.nextInt(4);
		if (n == 0) {
			Sound.handgun_shot1.playSound();
		}
		if (n == 1) {
			Sound.handgun_shot2.playSound();
		}
		if (n == 2) {
			Sound.handgun_shot3.playSound();
		}
		if (n == 3) {
			Sound.handgun_shot4.playSound();
		}
		shotsLeft--;
		return shotsLeft;

	}

}
