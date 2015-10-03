package com.irgndsondepp.clone.entity.projectile;

/**
 * the shotgun projectile class extending gun projectile. it uses the same
 * sprite but calculates the damage differently
 * 
 * @author Robert
 *
 */
public class ShotgunProjectile extends GunProjectile {

	/**
	 * create a shotgun projectile with a different damage calculation
	 * 
	 * @param x
	 *            is the x position
	 * @param y
	 *            is the y position in the level
	 * @param dam
	 *            is the damage value
	 * @param speed
	 *            is the speed of the projectile
	 * @param dir
	 *            is the direction as an angle
	 */
	public ShotgunProjectile(int x, int y, int dam, int speed, double dir) {
		super(x, y, dam, speed, dir);
		this.range = 200;
	}

	/**
	 * get the damage value of the projectile. the damage of a shotgun
	 * projectile is calculated using the travel distance. it does a minimum
	 * amount of damage of 10.
	 */
	public double getDamage() {
		double damModifier = (range - distance()) / range;

		double actualDamage = this.damage * damModifier;
		if (actualDamage <= 10.f) {
			actualDamage = 10.f;
		}
		return (actualDamage);
	}
}
