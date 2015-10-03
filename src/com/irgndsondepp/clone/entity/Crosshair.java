package com.irgndsondepp.clone.entity;

import com.irgndsondepp.clone.graphics.Sprite;

/**
 * creates a crosshair on screen
 * 
 * @author Robert
 *
 */
public class Crosshair extends MousePointer {

	/**
	 * creates a new crosshair as a subclass of mousepointer at the given
	 * coordinates on screen
	 * 
	 * @param x
	 * @param y
	 */
	public Crosshair(int x, int y) {
		super(x, y, Sprite.crosshairCursor);
	}

}
