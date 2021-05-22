package com.irgndsondepp.clone.level;

import java.util.Random;

import com.irgndsondepp.clone.Game;

public class RandomLevel extends Level {

	private static final Random random = new Random();

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
					tiles[x + y * width] = 0xff00ff00;
				if (rand == 1)
					tiles[x + y * width] = 0xff000000;
				if (rand == 2)
					tiles[x + y * width] = 0xffffff00;
				if (rand == 3)
					tiles[x + y * width] = 0xffff0000;
			}
		}
	}

}
