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
		int w = Game.width;
		int h = Game.height;

		// add the different options
		options.add(new MenuOption(w / 5, 16 * h / 30, 0, "Start Game"));
		options.add(new MenuOption(w / 5, 20 * h / 30, 1, "Highscores"));
		options.add(new MenuOption(w / 5, 24 * h / 30, 2, "Exit"));
		
		possibleSelectionOptions = options.size();
		
		if (Game.width < 600) {
			background = SpriteSheet.mainMenuBackground;
		} else {
			background = SpriteSheet.mainMenuBackgroundBig;
		}
	}	

}
