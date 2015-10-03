package com.irgndsondepp.clone.entity.projectile;

import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.Sprite;

/**
 * create a cat projectile
 * @author Robert
 *
 */
public class CatProjectile extends DudeProjectile {

	public static int FIRE_RATE = 30;

	// private boolean removed;
	/**
	 * creates a cat projectile
	 * @param x is x position
	 * @param y is y position
	 * @param dir is the direction as an angle
	 * @param range is the range of the projectile
	 */
	public CatProjectile(int x, int y, double dir, int range) {
		super(x, y, dir);
		range = 80;
		speed = 4;
		damage = 10;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		sprite = Sprite.projectile_cat;
	}

	/**
	 * move the projectile in the update method
	 */
	public void update() {
		move();
	}

	/**
	 * move the projectile if no collision detected or out of range
	 */
	protected void move() {
		if (!level.tileCollision(x, y, nx, ny, sprite.SIZE)
				&& !level.playerShot(this, (int) (x + nx), (int) (y + ny))) {
			x += nx;
			y += ny;
			if (distance() > range) {
				this.remove();
			}
		} else {
			this.remove();
			level.add(new ParticleSpawner((int) x, (int) y, 20, 50, level));
		}

	}
}
