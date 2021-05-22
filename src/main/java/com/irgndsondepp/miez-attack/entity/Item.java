package com.irgndsondepp.clone.entity;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;

/**
 * creates an item with a sprite at a certain position
 * 
 * @author Robert
 *
 */
public class Item extends Entity {

	private int scale = 1;

	/**
	 * create an item
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param sprite
	 *            is the sprite
	 */
	public Item(int x, int y, Sprite sprite) {
		super();
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public void update() {

	}

	/**
	 * return sprite
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * set the x coordinate
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * set the y coordinate
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * render the object
	 */
	public void render(Screen screen) {
		screen.renderItem(x, y, this, true, true);
	}

	/**
	 * set the scale to a number
	 * 
	 * @param x
	 */
	public void setScale(int x) {
		if (x >= 1) {
			scale = x;
		} else {
			scale = 1;
		}
	}

	/**
	 * return the scale
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;
	}

}
