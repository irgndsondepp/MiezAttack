package com.irgndsondepp.clone.level;

/**
 * create a tile coordinate for easier calculations
 * @author Robert
 *
 */
public class TileCoordinate {

	private int x, y;
	private final int TILE_SIZE = 16;

	/**
	 * get true screen coordinates from tile x and y
	 * @param x
	 * @param y
	 */
	public TileCoordinate(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}

	/**
	 * return the x value
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * return the y value
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * return an array with x and y value
	 * @return
	 */
	public int[] getXY() {
		int[] r = new int[2];
		r[0] = x;
		r[1] = y;
		return r;
	}

}
