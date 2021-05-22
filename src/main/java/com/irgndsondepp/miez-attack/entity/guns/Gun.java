package com.irgndsondepp.clone.entity.guns;

import com.irgndsondepp.clone.entity.Item;
import com.irgndsondepp.clone.entity.projectile.GunProjectile;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * create a gun with a firerate, shots left, maximum shots, damage and speed of
 * the spawned projectiles
 * 
 * @author Robert
 *
 */
public abstract class Gun extends Item {
	private int fireRate;
	protected int shotsLeft;
	public final int shotsTotal;
	protected int damage;
	protected int speed;

	/**
	 * create a gun
	 * 
	 * @param x
	 *            is the x position in the level
	 * @param y
	 *            is the y position in the level
	 * @param shots
	 *            is the number of shots
	 * @param damage
	 *            is the damage
	 * @param fireRate
	 *            is the fire rate of the gun
	 * @param speed
	 *            is the speed of the projectiles
	 * @param sprite
	 *            is the used sprite to display the gun
	 */
	public Gun(int x, int y, int shots, int damage, int fireRate, int speed,
			Sprite sprite) {
		super(x, y, sprite);
		this.fireRate = fireRate;
		this.damage = damage;
		this.speed = speed;
		this.shotsTotal = this.shotsLeft = shots;
	}

	/**
	 * return the fire rate
	 * 
	 * @return
	 */
	public int getFireRate() {
		return fireRate;
	}

	/**
	 * remove this object, if there are no shots left
	 */
	public void update() {
		if (shotsLeft <= 0) {
			this.remove();
		}

	}

	/**
	 * return the shots left in the gun
	 * 
	 * @return
	 */
	public int getShotsLeft() {
		return shotsLeft;
	}

	/**
	 * return the maximum number of shots
	 * 
	 * @return
	 */
	public int getShotsTotal() {
		return shotsTotal;
	}

	/**
	 * shoot the gun, spawning a new gun projectile with
	 * 
	 * @param x
	 *            an x coordinate in the level
	 * @param y
	 *            a y coordinate in the level
	 * @param dir
	 *            a direction as an angle
	 * @param level
	 *            the level
	 * @return return the value of shots left in the gun
	 */
	public int shoot(int x, int y, double dir, Level level) {
		level.add(new GunProjectile(x, y, damage, speed, dir));
		shotsLeft--;
		return shotsLeft;

	}

}
