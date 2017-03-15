package com.irgndsondepp.clone.graphics;

/**
 * overlays the gameoverscreen
 * @author Robert
 *
 */
public class gameOverScreen {

	public SpriteSheet message;

	public static gameOverScreen gameOver = new gameOverScreen(
			SpriteSheet.mauled);

	public static gameOverScreen gameOverBig = new gameOverScreen(
			SpriteSheet.mauledBig);

	/**
	 * create a gameoverscreen with a spritesheet containing a game over message
	 * @param sheet
	 */
	public gameOverScreen(SpriteSheet sheet) {
		message = sheet;
	}

	/**
	 * render the message on screen
	 * @param screen
	 */
	public void render(Screen screen) {
		screen.renderSpriteSheet(0, 0, message, false);
	}

}
