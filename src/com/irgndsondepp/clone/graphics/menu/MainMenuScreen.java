package com.irgndsondepp.clone.graphics.menu;

import com.irgndsondepp.clone.Game;

import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * the mainmenuscreen class extending menuscreen
 * @author Robert
 *
 */
public class MainMenuScreen extends MenuScreen {

	/**
	 * creates a new main menu screen including 3 options: start game, highscores, exit
	 * @param game
	 */
	public MainMenuScreen(Game game) {
		super(game);
		selectedOption = 0;
		int w = game.width;
		int h = game.height;

		// add the different options
		options.add(new MenuOption(w / 5, 16 * h / 30, 0, "Start Game"));
		options.add(new MenuOption(w / 5, 20 * h / 30, 1, "Highscores"));
		options.add(new MenuOption(w / 5, 24 * h / 30, 2, "Exit"));
		
		possibleSelectionOptions = options.size();
		
		if (game.width < 600) {
			background = SpriteSheet.mainMenuBackground;
		} else {
			background = SpriteSheet.mainMenuBackgroundBig;
		}

	}

	/**
	 * change the option up a step
	 */
	public void up() {
		if (wait > time) {
			changeSelectedOption(-1);
			wait = 0;
		}
	}

	/**
	 * change the option down a step
	 */
	public void down() {
		if (wait > time) {
			changeSelectedOption(1);
			wait = 0;
		}
	}

	/**
	 * select an option
	 */
	public void select() {
		if (selectedOption == 0 && wait > time) {
			deactivate();

			game.makeNewLevel();
		}
		if (selectedOption == 1 && wait > time) {
			game.setMenuScreen(new HighscoreScreen(width, height, game));
		}
		if (selectedOption == 2 && wait > time) {
			game.stop();
		}
	}

	/**
	 * change the selection in one direction including a jump step from the bottom to the top and reversed
	 */
	protected void changeSelectedOption(int delta) {
		selectedOption += delta;
		if (selectedOption < 0) {
			selectedOption = possibleSelectionOptions - 1;
		}
		if (selectedOption >= possibleSelectionOptions) {
			selectedOption = 0;
		}
	}

}
