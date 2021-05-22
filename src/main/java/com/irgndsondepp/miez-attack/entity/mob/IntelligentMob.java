package com.irgndsondepp.clone.entity.mob;

/**
 * the intelligent mob class of a chaser, that can go around and over solid
 * objects
 * 
 * @author Robert
 *
 */
public class IntelligentMob extends Dummy {

	/**
	 * call the super constructor
	 * 
	 * @param x
	 * @param y
	 */
	public IntelligentMob(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	/**
	 * update the movement
	 */
	public void update() {
		think();
		super.update();
	}

	/**
	 * find the optimal path to the player
	 */
	private void think() {
		// find optimal route to player

	}

}
