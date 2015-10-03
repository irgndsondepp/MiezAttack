package com.irgndsondepp.clone.entity;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.input.Mouse;

/**
 * Mouse pointer for using the mouse with specific sprites in game
 * 
 * @author Robert
 *
 */
public class MousePointer extends Entity {

	/**
	 * to create a mousepointer an initial x and y position is needed and also a
	 * sprite
	 * 
	 * @param x
	 *            is x coordinate
	 * @param y
	 *            is y coordinate
	 * @param sprite
	 *            is a sprite
	 */
	public MousePointer(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	/**
	 * updates the position of the mousepointer
	 */
	public void update() {
		double mx = Mouse.getX();
		double my = Mouse.getY();
		if (level != null) {
			// Position needs to be adapted to the actual screen size
			double sx = level.getScreenWidth();
			double sy = level.getScreenHeight();
			double w = Game.width;
			double h = Game.height;

			x = (int) ((mx / sx) * w);
			y = (int) ((my / sy) * h);

		}
	}

	/**
	 * renders the mousepointer as a fixed object on screen
	 */
	public void render(Screen screen) {

		screen.renderSprite(x - sprite.SIZE / 2, y - sprite.SIZE / 2, sprite,
				false);

	}

}
