package com.irgndsondepp.clone.entity;

import java.util.Random;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.Level;

/**
 * create an entitiy. entities have an x and y position on screen. they can be
 * active or removed (boolean) and have access to the level. they also have a
 * random generator and a sprite (which can be null for invisible entities).
 * also hitboxes are set here.
 * 
 * @author Robert
 *
 */
public abstract class Entity {
	protected int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;

	// Hitboxes
	protected int leftEdge = 0;
	protected int rightEdge = 0;
	protected int topEdge = 0;
	protected int bottomEdge = 0;

	/**
	 * the update method is empty
	 */
	public void update() {

	}

	/**
	 * the render method is empty
	 * 
	 * @param screen
	 */
	public void render(Screen screen) {

	}

	/**
	 * sets this entity as removed, so it will be deleted from different lists
	 * in the level.
	 */
	public void remove() {
		// Remove from level
		removed = true;
	}

	/**
	 * check if the item is still active or removed
	 * 
	 * @return
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * set the level
	 * 
	 * @param level
	 */
	public void init(Level level) {
		this.level = level;
	}

	/**
	 * get the x position
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * get the y position
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * returns the left edge of the hitbox
	 * 
	 * @return
	 */
	public int getLeftEdge() {
		return leftEdge;
	}

	/**
	 * returns the right edge of the hitbox
	 * 
	 * @return
	 */
	public int getRightEdge() {
		return rightEdge;
	}

	/**
	 * returns the top edge of the hitbox
	 * 
	 * @return
	 */
	public int getTopEdge() {
		return topEdge;
	}

	/**
	 * returns the bottom edge of the hitbox
	 * 
	 * @return
	 */
	public int getBottomEdge() {
		return bottomEdge;
	}

	/**
	 * returns the sprite
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

}
