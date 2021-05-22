package com.irgndsondepp.clone.graphics.menu;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * creates a settings screen, where the volume of sound and music can be
 * adjusted and fullscreen turned on or off
 * 
 * @author Robert
 *
 */
public class OptionsScreen extends MenuScreen {

	// TODO not implemented yet, missing saveable options class

	/**
	 * an options screen is created
	 * 
	 * @param game
	 *            is the running game
	 */
	public OptionsScreen(Game game) {
		super(game);
		selectedOption = 0;
		int w = Game.width;
		int h = Game.height;
		// has 4 options built in
		options.add(new MenuOption(w / 5, 16 * h / 30, 0, "Volume Sound"));
		options.add(new MenuOption(w / 5, 20 * h / 30, 1, "Volume Music"));
		options.add(new MenuOption(w / 5, 24 * h / 30, 2, "Fullscreen"));
		options.add(new MenuOption(w / 5, 28 * h / 30, 2, "Back"));
		possibleSelectionOptions = options.size();
		if (Game.width < 600) {
			background = SpriteSheet.subMenuBackground;
		} else {
			background = SpriteSheet.subMenuBackgroundBig;
		}

	}

	/**
	 * what happens if the user presses the up key
	 */
	public void up() {
		if (wait > time) {
			changeSelectedOption(-1);
			wait = 0;
		}
	}

	/**
	 * what happens if the user presses the down key
	 */
	public void down() {
		if (wait > time) {
			changeSelectedOption(1);
			wait = 0;
		}
	}

	/**
	 * what happens if the user presses the select key
	 */
	public void select() {
		if (selectedOption == 0 && wait > time) {

		}
		if (selectedOption == 1 && wait > time) {

		}
		if (selectedOption == 2 && wait > time) {

		}
		if (selectedOption == 3 && wait > time) {
			game.setMenuScreen(new MainMenuScreen(game));
		}
	}

	/**
	 * updates the currently selected option
	 * 
	 * @param delta
	 *            the change in int
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
