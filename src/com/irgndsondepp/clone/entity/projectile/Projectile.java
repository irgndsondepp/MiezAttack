package com.irgndsondepp.clone.entity.projectile;

import java.util.Random;

import com.irgndsondepp.clone.entity.Entity;

/**
 * the abstract class projectile extending entity. a projectile has a origin and
 * an angle, a sprite, x and y position, speed, range, damage...
 * 
 * @author Robert
 *
 */
public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double x, y;
	protected double nx, ny;
	protected double speed, range, damage;
	protected final Random random = new Random();

	/**
	 * create a projectile at (x,y) with a direction as an angle. set the origin
	 * to (x,y) to calculate the trajectory. also x,y are double values to move
	 * the projectile smoother.
	 * 
	 * @param x
	 * @param y
	 * @param dir
	 */
	public Projectile(int x, int y, double dir) {
		this.xOrigin = x;
		this.yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;

	}

	/**
	 * an empty move method
	 */
	protected void move() {

	}

	/**
	 * return the x position casted to int
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * return the y position casted to int
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * return the damage value
	 * 
	 * @return
	 */
	public double getDamage() {
		return damage;
	}

}
