package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.GunProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * create a machinegun extending the class gun
 * 
 * @author Robert
 *
 */
public class MachineGun extends Gun {

	/**
	 * create a machinegun at (x,y) in the level. a machinegun has a specific
	 * sprite and specific values, which are used with the gun super class
	 * constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public MachineGun(int x, int y) {
		super(x, y, 50, 10, 5, 12, Sprite.machine_gun);
	}

	/**
	 * override the shoot method of the super class to spawn a gun projectile
	 * and play only one soundfile from the handgun sounds
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new GunProjectile(x, y, damage, speed, dir));
		Sound.handgun_shot1.playSound();
		shotsLeft--;
		return shotsLeft;

	}

}
