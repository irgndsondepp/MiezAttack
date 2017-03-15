package com.irgndsondepp.clone.graphics.menu;

import java.util.ArrayList;
import java.util.List;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * creates a menuscreen extending the screen class. the menuscreen can be active
 * or inactive and has different options on it.
 * 
 * @author Robert
 *
 */
public class MenuScreen extends Screen {

	protected boolean active;
	protected int selectedOption;
	protected int possibleSelectionOptions;
	protected Game game;
	protected SpriteSheet background;
	protected int wait;
	protected int time;
	Sprite selectionCursor;

	List<MenuOption> options = new ArrayList<MenuOption>();

	/**
	 * create a new menuscreen
	 * 
	 * @param game
	 */
	public MenuScreen(Game game) {
		super(Game.width, Game.height);
		this.game = game;
		menu = true;
		// a new menuscreen is always active at first
		activate();
		wait = 0;
		time = Game.framerate / 4;
		selectionCursor = Sprite.menuSelectionCursor;
	}

	/**
	 * the update method increases the wait timer (for chosing options)
	 */
	public void update() {
		wait++;
		if (wait > time) {
			wait = time + 1;
		}
	}

	/**
	 * draw the menu screen
	 */
	public void render() {
		clear();
		renderBackground();
		renderOptions();
	}

	/**
	 * draw the background of the menuscreen
	 */
	protected void renderBackground() {
		if (background != null) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x < 0 || x >= width || y < 0 || y >= height)
						continue;
					pixels[x + y * width] = background.getPixel(x, y);
				}
			}
		}
	}

	/**
	 * overlay the cursor at the selected option coordinates
	 */
	protected void renderOptions() {
		int xp = 0;
		int yp = 0;
		for (int i = 0; i < options.size(); i++) {
			MenuOption option = options.get(i);
			if (option.getPlace() == selectedOption) {
				xp = option.getX();
				yp = option.getY();
			}
		}
		Sprite cursor = Sprite.menuSelectionCursor;
		int[] pixels = cursor.pixels;
		for (int y = 0; y < cursor.SIZE; y++) {
			for (int x = 0; x < cursor.SIZE; x++) {
				if ((xp + x) < 0 || (xp + x) >= width || (y + yp) < 0 || (y + yp) >= height)
					continue;
				int color = pixels[x + y * cursor.SIZE];
				if (color != 0xffff00ff) {
					this.pixels[x + xp + (y + yp) * width] = pixels[x + y * cursor.SIZE];
				}
			}
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
	 * empty method
	 */
	public void left() {

	}

	/**
	 * empty method
	 */
	public void right() {

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
	 * change the selection in one direction including a jump step from the
	 * bottom to the top and reversed
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

	/**
	 * exits the game
	 */
	public void exit() {
		game.stop();
	}

	/**
	 * check if the menuscreen is active
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * set the menuscreen as inactive
	 */
	public void deactivate() {
		active = false;
	}

	/**
	 * set the menuscreen as active
	 */
	public void activate() {
		active = true;
	}

}
