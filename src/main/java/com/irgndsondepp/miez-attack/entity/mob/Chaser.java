package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.audio.Sound;

import com.irgndsondepp.clone.entity.projectile.CatProjectile;

import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;

/**
 * creates a basic ai, that chases the player without pathfinding
 * 
 * @author Robert
 *
 */
public class Chaser extends Dummy {

	protected boolean chasing = false;

	protected int fireRate;
	protected int maulDamage = 15;
	protected int agroRadius = 120;
	protected int shotDistance = 80;
	protected int minimumShotDistance = 5;

	protected Player player = null;

	/**
	 * create a chaser
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Chaser(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
		hitpoints = 50;
		fireRate = 0;
	}

	/**
	 * update the chaser movement and time
	 */
	public void update() {

		chase();

		time++;

		// die
		if (this.hitpoints <= 0) {
			this.remove();
			Sound.cat_die.playSound();
			level.add(new ParticleSpawner(x - 8, y - 8, 50, 500, 0xff00ff,
					level));
		}

		// fire cooldown
		if (fireRate > 0) {
			fireRate--;
		} else {
			fireRate = 0;
		}

		// move randomly
		if (time % (random.nextInt(50) + 30) == 0 && !(chasing)) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(5) == 0) {
				xa = 0;
				ya = 0;
			}
		}

		animSprite.update();
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {

			animSprite = right;
			dir = Direction.RIGHT;
		}

		if (ya < 0) {

			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {

			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
			animSprite = idle;
			dir = Direction.IDLE;
		}
	}

	/**
	 * chase the player if in aggro range and shoot if in range
	 */
	private void chase() {

		if (level.getClientPlayer() != null) {

			int xp = level.getClientPlayer().getX();
			int yp = level.getClientPlayer().getY();
			int dx = xp - x;
			int dy = yp - y;
			if (dx < 0) {
				dx = -dx;
			}
			if (dy < 0) {
				dy = -dy;
			}
			if (!chasing) {
				// start chase
				if (dx <= agroRadius && dy <= agroRadius) {
					chasing = true;
					player = level.getClientPlayer();
				}
			} else {
				// give up chase
				if (dx >= 2 * agroRadius && dy >= 2 * agroRadius) {
					chasing = false;
				} else {
					xa = 0;
					ya = 0;
					if (x < xp)
						xa++;
					if (x > xp)
						xa--;
					if (y < yp)
						ya++;
					if (y > yp)
						ya--;
				}
			}

			// fire if in range
			if (chasing && fireRate <= 0 && dx <= shotDistance
					&& dy <= shotDistance && dx > minimumShotDistance
					&& dy > minimumShotDistance) {
				updateShooting();
			}

		}
	}

	/**
	 * calculate trajectory
	 */
	protected void updateShooting() {
		if (!level.getClientPlayer().isDead()) {
			double dx = level.getClientPlayer().getX() - x;
			double dy = level.getClientPlayer().getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = CatProjectile.FIRE_RATE;

		}
	}

	/**
	 * create a cat projectile aimed at the player
	 */
	protected void shoot(int x0, int y0, double dir) {
		Projectile p = new CatProjectile(x, y, dir, shotDistance);
		projectiles.add(p);
		level.add(p);

	}

	/**
	 * return melee damage
	 * 
	 * @return
	 */
	public int getMaulDamage() {
		return (maulDamage + random.nextInt(10));
	}
}
