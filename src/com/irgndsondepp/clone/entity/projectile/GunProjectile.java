package com.irgndsondepp.clone.entity.projectile;

import com.irgndsondepp.clone.graphics.Sprite;

/**
 * the gun projectile class extending a dude projectile
 * 
 * @author Robert
 *
 */
public class GunProjectile extends DudeProjectile {

	/**
	 * call the constructor for a dude projectile and set different values,
	 * because a gun projectile can travel farther and has a different sprite
	 * 
	 * @param x
	 *            is the x position
	 * @param y
	 *            is the y position in the level
	 * @param dam
	 *            is the damage
	 * @param speed
	 *            is the speed
	 * @param dir
	 *            is the direction as an angle
	 */
	public GunProjectile(int x, int y, int dam, int speed, double dir) {
		super(x, y, dam, speed, dir);
		this.range = 200;
		this.sprite = Sprite.projectile_gun;
		this.addLighting = false;
		leftEdge = 5;
		rightEdge = 7;
		topEdge = 6;
		bottomEdge = 6;
	}

}
