package com.irgndsondepp.clone.entity.projectile;

import com.irgndsondepp.clone.graphics.Sprite;

/**
 * create a cathulhu projectile
 * @author Robert
 *
 */
public class CathulhuProjectile extends CatProjectile {

	/**
	 * creates a Cathulhu projectile
	 * @param x is x position
	 * @param y is y position
	 * @param dir is the direction as an angle
	 * @param range is the range of the projectile
	 */
	public CathulhuProjectile(int x, int y, double dir, int range) {
		super(x, y, dir, range);
		range = 200;
		speed = 4;
		damage = 10;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		sprite = Sprite.projectile_dude;
	}

}
