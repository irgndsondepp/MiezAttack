package com.irgndsondepp.clone.entity.projectile;

import com.irgndsondepp.clone.entity.Explosion;
import com.irgndsondepp.clone.entity.mob.Mob;

/**
 * creates a rocket that has specific damage and creates an explosion on
 * collision
 * 
 * @author Robert
 *
 */
public class RocketProjectile extends DudeProjectile {

	/**
	 * the constructor for a rocket projectile
	 * 
	 * @param x
	 *            is the x coordinate
	 * @param y
	 *            is the y coordinate
	 * @param damage
	 *            is the damage of the projectile
	 * @param speed
	 *            is the speed
	 * @param dir
	 *            is the direction as an angle
	 */
	public RocketProjectile(int x, int y, int damage, int speed, double dir) {
		super(x, y, dir);
		this.damage = damage;
		this.speed = speed;
		// should be enough
		this.range = 1000;
	}

	/**
	 * this needs a special move() method that spawns explosions
	 */
	protected void move() {
		// if there is no collision with a tile
		// if there is no collision with a mob
		Mob mob = level.mobShot(this, (int) (x + nx), (int) (y + ny));
		if (!level.tileCollision(x, y, nx, ny, sprite.SIZE) && (mob == null)) {
			// move this thing
			x += nx;
			y += ny;
			// remove if it has reached maximum distance
			if (distance() > range) {
				this.remove();
			}
		} else {
			this.remove();
			// if there is a collision with a solid tile
			// create explosion at tile
			// if there is a collision with a mob
			// create explosion at the mobs center
			if (mob == null) {
				int xpos = (int) (x + nx);
				int ypos = (int) (y + ny);
				level.add(new Explosion(xpos, ypos, (int) damage, level));
			} else {
				int xpos = (int) mob.getX();
				int ypos = (int) mob.getY();
				level.add(new Explosion(xpos, ypos, (int) damage, level));
			}

		}

	}
}
