package com.irgndsondepp.clone.graphics.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * displays the highscore screen and fills it with the value from the highscore
 * class arrays. Also handles File IO and writes highscores to the folder
 * containing the executable as well as checking for present highscores there.
 * 
 * @author Robert
 *
 */
public class HighscoreScreen extends MenuScreen {

	private Highscores highscores;
	private boolean newHighscore;

	/**
	 * create a new highscore screen
	 * 
	 * @param width
	 *            the width of the screen
	 * @param height
	 *            the height of the screen
	 * @param game
	 *            the game
	 */
	public HighscoreScreen(int width, int height, Game game) {
		super(game);
		// check for resolution
		if (Game.width < 600) {
			background = SpriteSheet.subMenuBackground;
		} else {
			background = SpriteSheet.subMenuBackgroundBig;
		}
		options.add(new MenuOption(Game.width, Game.height, 0, "Back"));
		possibleSelectionOptions = options.size();
		selectedOption = 0;
		wait = 0;
		newHighscore = false;
		load();
	}

	/**
	 * create a new highscore screen and hand over the values the player reached
	 * in his game
	 * 
	 * @param width
	 *            the width of the screen
	 * @param height
	 *            the height of the screen
	 * @param kills
	 *            the number of kills by the player
	 * @param killStreak
	 *            the heighest kill streak of the player
	 * @param levelDestruction
	 *            the percentage of the level the player destroyed
	 * @param game
	 *            the game
	 */
	public HighscoreScreen(int width, int height, int kills, int killStreak,
			int levelDestruction, Game game) {
		super(game);
		// check for resolution
		if (Game.width < 600) {
			background = SpriteSheet.subMenuBackground;
		} else {
			background = SpriteSheet.subMenuBackgroundBig;
		}
		possibleSelectionOptions = 1;
		selectedOption = 0;
		wait = 0;
		options.add(new MenuOption(Game.width, Game.height, 0, "Back"));
		load();
		if (highscores.isNewHighscore(kills, killStreak, levelDestruction)) {
			newHighscore = true;
		} else
			newHighscore = false;
		save();
	}

	/**
	 * render the background, the scores and the options
	 */
	public void render() {
		renderBackground();
		renderScores();
		renderOptions();
		if (newHighscore && (wait < 5)) {
			renderCongratulations();
		}

	}

	/**
	 * check if the music is still playing and update the waiting time (so the
	 * cursor isn't jumping around)
	 */
	public void update() {
		wait++;
		if (Sound.level_music.isPlaying()) {
			Sound.level_music.stop();
		}

	}

	/**
	 * if enough time has passed, select the option
	 */
	public void select() {
		if (wait > 120) {
			game.setMenuScreen(new MainMenuScreen(game));
		}
	}

	/**
	 * empty method. scores are rendered in the game method.
	 */
	private void renderScores() {
		// TODO needs more rendering
	}

	/**
	 * empty method. no congratulations are rendered at the moment
	 */
	private void renderCongratulations() {
		// TODO render congratulations message
	}

	/**
	 * try to load previous highscores, if there are any. else create new
	 * highscores with default values.
	 */
	private void load() {
		try {
			String filePath = new File("").getAbsolutePath();
			String path = filePath + "/data/scores.ecs";
			path = "scores.ecs";
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			highscores = (Highscores) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException e) {
			System.out.println("Load didn't work!");
			highscores = new Highscores();
			save();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * save the highscores if possible, otherwise throw an IOException
	 */
	private void save() {
		try {
			String filePath = new File("").getAbsolutePath();
			String path = filePath + "/data/scores.ecs";
			path = "scores.ecs";
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(highscores);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Save didn't work!");
		}

	}

	public Highscores getHighscores() {
		return highscores;
	}

}
