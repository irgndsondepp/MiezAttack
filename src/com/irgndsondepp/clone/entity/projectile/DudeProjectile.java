package com.irgndsondepp.clone.entity.projectile;

import com.irgndsondepp.clone.entity.mob.Mob;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;

/**
 * the dude projectile class extending projectile
 * 
 * @author Robert
 *
 */
public class DudeProjectile extends Projectile {

	public static int FIRE_RATE = 30;
	public boolean addLighting =true;

	// private boolean removed;

	/**
	 * create a dude projectile at (x,y) in the level with a direction as an
	 * angle. a dude projectile has a standard sprite, speed, range and damage
	 * values.
	 * 
	 * @param x
	 * @param y
	 * @param dir
	 */
	public DudeProjectile(int x, int y, double dir) {
		super(x, y, dir);
		speed = 6;
		damage = 10;
		init();
	}

	/**
	 * create a dude projectile with different speed and damage
	 * 
	 * @param x
	 *            is the x position in the level
	 * @param y
	 *            is the y position
	 * @param dam
	 *            is the damage
	 * @param speed
	 *            is the speed of the projectile
	 * @param dir
	 *            is the direciton as an angle
	 */
	public DudeProjectile(int x, int y, int dam, int speed, double dir) {
		super(x, y, dir);
		range = 120;
		this.speed = speed;
		this.damage = dam;
		init();

	}

	/**
	 * initialize values, that are always the same for all dude projectiles
	 * (like the hitbox)
	 */
	public void init() {
		range = 120;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		sprite = Sprite.projectile_dude;

		// create hitbox
		leftEdge = 3;
		rightEdge = 4;
		topEdge = sprite.SIZE / 2;
		bottomEdge = 4;
	}

	/**
	 * call the move method
	 */
	public void update() {
		move();
	}

	/**
	 * render the projectile using the screen.renderProjectile method
	 */
	public void render(Screen screen) {
		screen.renderProjectile((int) x - 8, (int) y - 4, this);
		if (addLighting){
		screen.AddDynamicLighting((int) x, (int) y, 30, 500);
		}
	}

	/**
	 * move the projectile if no collision is detected and it has not moved more
	 * than it's range, otherwise add a particle spawner
	 */
	protected void move() {
		Mob mob = level.mobShot(this, (int) (x + nx), (int) (y + ny));
		if (!level.tileCollision(x, y, nx, ny, sprite.SIZE) && (mob == null)) {
			x += nx;
			y += ny;
			if (distance() > range) {
				this.remove();
			}
		} else {
			this.remove();
			int xpos = (int) x - sprite.SIZE / 2;
			int ypos = (int) y - sprite.SIZE / 2;
			level.add(new ParticleSpawner(xpos, ypos, 20, 50, level));
		}

	}

	/**
	 * calculate the traveled distance of the projectile
	 * 
	 * @return
	 */
	protected double distance() {
		double dist = 0;
		dist = Math.sqrt(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y)));
		return dist;
	}

}
