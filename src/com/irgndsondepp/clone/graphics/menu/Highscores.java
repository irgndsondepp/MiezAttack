package com.irgndsondepp.clone.graphics.menu;

import java.io.Serializable;

import javax.swing.JOptionPane;

/**
 * Highscores is a serializable class. This class holds all the top scores,
 * names and kill streak values as well as the percentage of level destroyed.
 * 
 * @author Robert
 *
 */
public class Highscores implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int[] kills = new int[10];
	public int[] killStreak = new int[10];
	public int[] levelDestruction = new int[10];
	public String[] names = new String[10];

	/**
	 * create new predefined highscores
	 */
	public Highscores() {
		kills[0] = 250;
		killStreak[0] = 250;
		levelDestruction[0] = 0;
		names[0] = "Da Miez";

		kills[1] = 168;
		killStreak[1] = 32;
		levelDestruction[1] = 0;
		names[1] = "Da Miez";

		kills[2] = 132;
		killStreak[2] = 64;
		levelDestruction[0] = 0;
		names[2] = "Da Miez";

		kills[3] = 131;
		killStreak[3] = 15;
		levelDestruction[3] = 0;
		names[3] = "Da Miez";

		kills[4] = 111;
		killStreak[4] = 100;
		levelDestruction[4] = 0;
		names[4] = "Da Miez";

		kills[5] = 98;
		killStreak[5] = 1;
		levelDestruction[5] = 0;
		names[5] = "Da Miez";

		kills[6] = 72;
		killStreak[6] = 21;
		levelDestruction[6] = 0;
		names[6] = "Da Miez";

		kills[7] = 11;
		killStreak[7] = 10;
		levelDestruction[7] = 0;
		names[7] = "Da Miez";

		kills[8] = 10;
		killStreak[8] = 10;
		levelDestruction[8] = 0;
		names[8] = "Da Miez";

		kills[9] = 1;
		killStreak[9] = 1;
		levelDestruction[9] = 0;
		names[9] = "Da Miez";
	}

	/**
	 * check if a new highscore is reached
	 * 
	 * @param kills
	 *            the number of kills
	 * @param killStreak
	 *            the highest kill streak number
	 * @param levelDestruction
	 *            percentage of the level destroyed
	 * @return true if a new highscore was reached, else false
	 */
	public boolean isNewHighscore(int kills, int killStreak,
			int levelDestruction) {
		boolean newHighscore = false;
		boolean enteredName = false;
		String name = "Froschn";
		for (int i = this.kills.length - 1; i >= 0; i--) {
			if (this.kills[i] < kills) {
				newHighscore = true;
				if (!enteredName) {
					name = JOptionPane
							.showInputDialog("Please, enter your name:");
					enteredName = true;
				}
				if (i < this.kills.length - 1) {
					this.kills[i + 1] = this.kills[i];
					this.killStreak[i + 1] = this.killStreak[i];
					this.levelDestruction[i + 1] = this.levelDestruction[i];

					names[i + 1] = names[i];
				}
				this.kills[i] = kills;
				this.killStreak[i] = killStreak;
				this.levelDestruction[i] = levelDestruction;
				names[i] = name;

			}
		}
		return newHighscore;
	}

}
