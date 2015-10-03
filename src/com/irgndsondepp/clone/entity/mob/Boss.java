package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.Game;

/**
 * creates a boss enemy extending chaser with different chasing behavior and
 * possibility to get stunned
 * 
 * @author Robert
 *
 */
public class Boss extends Chaser {

	protected double maxHitpoints;
	protected boolean stunned = false;
	protected int stunTime = 0;
	protected int stunTimeMax = 1 * Game.framerate;

	/**
	 * constructor for a Boss at (x,y) in tile coordinates
	 * 
	 * @param x
	 *            is x coordinate
	 * @param y
	 *            is y coordinate
	 */
	public Boss(int x, int y) {
		super(x, y);
	}

	/**
	 * changed chase() method so the player is automatically chased
	 */
	protected void chase() {

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

				chasing = true;
				player = level.getClientPlayer();

			}

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

			if (chasing && fireRate <= 0 && dx <= shotDistance
					&& dy <= shotDistance && dx > minimumShotDistance
					&& dy > minimumShotDistance) {
				updateShooting();
			}

		}
	}

	/**
	 * returns the maximum hitpoints of the boss
	 * 
	 * @return double maxHitpoints
	 */
	public double getMaxHP() {
		return maxHitpoints;
	}

}
