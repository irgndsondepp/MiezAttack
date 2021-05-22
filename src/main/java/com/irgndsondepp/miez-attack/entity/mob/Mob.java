package com.irgndsondepp.clone.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.Entity;
import com.irgndsondepp.clone.entity.projectile.DudeProjectile;
import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.graphics.Screen;

/**
 * create a mobile entity
 * 
 * @author Robert
 *
 */
public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	protected double hitpoints;
	protected double maxHitpoints;
	protected int speed;
	protected int spriteSize;
	protected int scale = 1;
	protected Sound soundGetHit;
	protected Sound soundHit;
	protected Sound soundDie;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT, IDLE
	}

	protected Direction dir;

	// for experience
	protected List<Projectile> projectiles = new ArrayList<Projectile>();

	/**
	 * move the mob if no collision is detected
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
			this.x += xchange;
			this.y += ychange;
		}

	}

	/**
	 * prototype update method
	 */
	public abstract void update();

	/**
	 * prototype render method
	 */
	public abstract void render(Screen screen);

	/**
	 * check collision
	 * 
	 * @param xchange
	 *            change in x direction
	 * @param ychange
	 *            change in y direction
	 * @return
	 */
	protected boolean collision(int xchange, int ychange) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {

			int xt = 0;
			int yt = 0;
			if (c == 0) {
				// top left corner
				xt = ((x + xchange) - (spriteSize / 2 - leftEdge)) >> 4;
				yt = ((y + ychange) - (spriteSize / 2 - topEdge)) >> 4;
			} else if (c == 1) {
				// top right corner
				xt = ((x + xchange) + (spriteSize / 2 - rightEdge)) >> 4;
				yt = ((y + ychange) - (spriteSize / 2 - topEdge)) >> 4;
			} else if (c == 2) {
				// bottom left corner
				xt = ((x + xchange) - (spriteSize / 2 - leftEdge)) >> 4;
				yt = ((y + ychange) + (spriteSize / 2 - bottomEdge)) >> 4;
			} else {
				// bottom right corner
				xt = ((x + xchange) + (spriteSize / 2 - rightEdge)) >> 4;
				yt = ((y + ychange) + (spriteSize / 2 - bottomEdge)) >> 4;

			}
			if (level.getTile(xt, yt).solid())
				solid = true;

		}
		if (level.mobCollision(this, x, xchange, y, ychange)) {
			solid = true;
		}
		return solid;
	}

	/**
	 * shoot a projectile
	 * 
	 * @param x0
	 *            x origin
	 * @param y0
	 *            y origin
	 * @param dir
	 *            direction as an angle
	 */
	protected void shoot(int x0, int y0, double dir) {
		Projectile p = new DudeProjectile(x, y, dir);
		projectiles.add(p);
		level.add(p);

	}

	/**
	 * remove damage from hitpoints and play sound
	 * 
	 * @param dmg
	 */
	public void getHit(double dmg) {
		if (soundGetHit != null && !(soundGetHit.isPlaying())) {
			soundGetHit.playSound();
		}
		hitpoints -= dmg;
		if (hitpoints <= 0) {
			if (soundDie != null && !(soundDie.isPlaying())) {
				if (soundGetHit != null && soundGetHit.isPlaying()) {
					soundGetHit.stop();
				}
				soundDie.playSound();
			}
			this.remove();
		}
	}

	/**
	 * get the hitpoints
	 * 
	 * @return
	 */
	public int getHP() {
		return (int) hitpoints;
	}

	/**
	 * returns the scaled spriteSize
	 * 
	 * @return
	 */
	public int getSpriteSize() {
		return spriteSize;
	}

	/**
	 * returns the scale
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;

	}

	/**
	 * play Soundfile if hitting an enemy
	 */
	public void playSoundHit() {
		if (soundHit != null && !(soundHit.isPlaying())) {
			soundHit.playSound();
		}
	}

}
