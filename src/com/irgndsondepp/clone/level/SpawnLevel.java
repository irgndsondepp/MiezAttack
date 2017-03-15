package com.irgndsondepp.clone.level;

import com.irgndsondepp.clone.Game;

/**
 * creates a SpawnLevel
 * 
 * @author Robert
 *
 */
public class SpawnLevel extends Level {

	/**
	 * creates a SpawnLevel loaded from a *.png image file
	 * 
	 * @param path
	 *            path to *.png file as string
	 * @param game
	 *            the game object
	 */
	public SpawnLevel(String path, Game game) {

		super(path, game);
	}

	/**
	 * loads the level image stored in a *.png file and adds enemies
	 */
	protected void loadLevel(String path) {
		super.loadLevel(path);

		int amountDummies = ((int) (0.9 * Game.startingMiezen));
		addDummies(amountDummies);

		int amountChasers = Game.startingMiezen - amountDummies;
		addChasers(amountChasers);
	}

	private void addChasers(int amount) {
		for (int i = 0; i < amount; i++) {
			spawnChaser();
		}
	}

	private void addDummies(int amount) {
		for (int i = 0; i < amount; i++) {
			spawnDummy();
		}
	}
}
