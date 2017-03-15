package com.irgndsondepp.clone.level;

import java.util.Random;

import com.irgndsondepp.clone.Game;

public class RandomLevel extends Level {

	private static final Random random = new Random();
	final private int grassTile = 0xff00ff00;
	final private int rockTile = 0xff000000;
	final private int flowerTile = 0xffffff00;
	final private int treeTile = 0xffff0000;
	
	/**
	 * create a random level, meaning a level with an array of random tiles. currently unused.
	 * @param width
	 * @param height
	 * @param game
	 */
	public RandomLevel(int width, int height, Game game) {
		super(width, height, game);
	}

	// override der Super methode
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rand = random.nextInt(4);
				if (rand == 0)
					tiles[x + y * width] = grassTile;
				if (rand == 1)
					tiles[x + y * width] = rockTile;
				if (rand == 2)
					tiles[x + y * width] = flowerTile;
				if (rand == 3)
					tiles[x + y * width] = treeTile;
			}
		}
	}

}
