package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.projectile.CathulhuProjectile;
import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.AnimatedSprite;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * creates a cathulhu enemy extending boss
 * 
 * @author Robert
 *
 */
public class Cathulhu extends Boss {
	protected AnimatedSprite down = new AnimatedSprite(
			SpriteSheet.cathulhu_down, 32, 32, 3);
	protected AnimatedSprite up = new AnimatedSprite(SpriteSheet.cathulhu_up,
			32, 32, 3);
	protected AnimatedSprite left = new AnimatedSprite(
			SpriteSheet.cathulhu_left, 32, 32, 3);
	protected AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.cathulhu_right, 32, 32, 3);

	/**
	 * constructor for a cathulhu enemy
	 * 
	 * @param x
	 *            is x coordinate
	 * @param y
	 *            is y coordinate
	 */
	public Cathulhu(int x, int y) {
		super(x, y);
		this.hitpoints = 2500;
		maxHitpoints = this.hitpoints;
		// should be bigger than normal
		scale = 2;
		this.sprite = Sprite.cathulhu_default;
		this.spriteSize = scale * sprite.SIZE;
		// bigger shotDistance
		shotDistance = 200;
		minimumShotDistance = 5;

		// more melee damage
		maulDamage = 35;

		// create hitbox
		leftEdge = 4 * scale;
		rightEdge = 5 * scale;
		topEdge = 10 * scale;
		bottomEdge = 1 * scale;

		Sound.cathulhu_scream.playSound();
		this.soundGetHit = Sound.cathulhu_hit;
		this.soundDie = Sound.cathulhu_die;
		this.soundHit = Sound.player_hit;
	}

	/**
	 * update method for cathulhu with sounds
	 */
	public void update() {
		chase();

		time++;

		if (this.hitpoints <= 0) {
			this.remove();
			if (Sound.cathulhu_hit.isPlaying()) {
				Sound.cathulhu_hit.stop();
			}
			Sound.cathulhu_die.playSound();
			level.add(new ParticleSpawner(x, y, 150, 1500, 0xff00ff, level));
		}

		if (fireRate > 0) {
			fireRate--;
		} else {
			fireRate = 0;
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

		// only move if not stunned
		if (!stunned) {
			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
				animSprite = down;
				dir = Direction.DOWN;
			}
		} else {
			stunTime++;
			if (stunTime >= stunTimeMax) {
				stunned = false;
				stunTime = 0;
			}
		}
	}

	/**
	 * moves the enemy if no collision is detected
	 * 
	 * @param xchange
	 *            change in x direction
	 * @param ychange
	 *            change in y direction
	 */
	public void move(int xchange, int ychange) {
		if (xchange != 0 && ychange != 0) {
			move(xchange, 0);
			move(0, ychange);
			return;
		}
		if (xchange > 0)
			dir = Direction.RIGHT;
		if (xchange < 0)
			dir = Direction.LEFT;
		if (ychange > 0)
			dir = Direction.DOWN;
		if (ychange < 0)
			dir = Direction.UP;

		if (!collision(xchange, ychange)) {
			this.x += xchange * speed;
			this.y += ychange * speed;
		}

	}

	/**
	 * checks for collision and destroys tiles, should override mob collision
	 * detection
	 * 
	 * @param xchange
	 *            change in x direction
	 * @param ychange
	 *            change in y direction
	 * @return
	 */
	protected boolean collision(int xchange, int ychange) {
		boolean solid = false;
		int xtmin = ((x + xchange) - (spriteSize / 2 - leftEdge)) >> 4;
		int ytmin = ((y + ychange) - (spriteSize / 2 - topEdge)) >> 4;
		int xtmax = ((x + xchange) + (spriteSize / 2 - rightEdge)) >> 4;
		int ytmax = ((y + ychange) + (spriteSize / 2 - bottomEdge)) >> 4;

		for (int y = ytmin; y <= ytmax; y++) {
			for (int x = xtmin; x <= xtmax; x++) {

				// destroy tiles in the way
				if (level.getTile(x, y).solid()) {
					solid = true;
					level.destroyTile(x, y);
					stunned = true;

				}

			}
		}
		if (solid) {

			Sound.explosion.playSound();

		}
		if (level.mobCollision(this, x, xchange, y, ychange)) {
			solid = false;
		}

		return solid;
	}

	/**
	 * shoots multiple cathulhu projectiles
	 */
	protected void shoot(int x0, int y0, double dir) {
		Projectile p = new CathulhuProjectile(x, y, dir, shotDistance);
		projectiles.add(p);
		level.add(p);
		p = new CathulhuProjectile(x, y, dir - 0.2, shotDistance);
		projectiles.add(p);
		level.add(p);
		p = new CathulhuProjectile(x, y, dir + 0.2, shotDistance);
		projectiles.add(p);
		level.add(p);

	}

	/**
	 * plays special sounds for cathulhu
	 */
	public void getHit(double dmg) {
		super.getHit(dmg);
		// don't overlap soundfiles
		if (!Sound.cathulhu_hit.isPlaying()) {
			Sound.cathulhu_hit.playSound();
		}
	}

}
