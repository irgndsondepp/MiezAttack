package com.irgndsondepp.clone.graphics.menu;

/**
 * create a menu option on a menu screen. this class handles the position of the pointer
 * @author Robert
 *
 */
public class MenuOption {
	private int x, y;
	private int n;
	private String name;

	/**
	 * create a menu option at the given coordinates
	 * @param x
	 * @param y
	 * @param n place in option chain
	 * @param name
	 */
	public MenuOption(int x, int y, int n, String name) {
		this.x = x;
		this.y = y;
		this.n = n;
		this.name = name;
	}

	public int getPlace() {
		return n;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}
}
