package com.irgndsondepp.clone.graphics;

/**
 * animated sprite class extending sprite. an animated sprite has a spritesheet
 * and changes the sprites after a time interval to show a short animation.
 * 
 * @author Robert
 *
 */
public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 10;
	private int time = 0;
	private int length = -1;
	private boolean played = false;

	/**
	 * an animated sprite has a spritesheet, width and height of the sheet and
	 * an animation length (in frames).
	 * 
	 * @param sheet
	 *            is the spritesheet with the animation
	 * @param width
	 *            the width of the spritesheet
	 * @param height
	 *            the height of the spritesheet
	 * @param length
	 *            the length of the animation in frames
	 */
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length - 1;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) {
			System.err.println("Error! Length of animation is too long!");
		}
		reset();
	}

	/**
	 * updates the active sprite.
	 */
	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length) {
				frame = 0;
				played = true;
			} else
				frame++;
			sprite = sheet.getSprites()[frame];
		}
	}

	/**
	 * returns the active sprite
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * returns the number of the frame (starting with 0)
	 * 
	 * @return
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * set the frame rate to an integer value
	 * 
	 * @param rate
	 */
	public void setFrameRate(int rate) {
		this.rate = rate;

	}

	/**
	 * set the frame of the animation to a value
	 * 
	 * @param index
	 */
	public void setFrame(int index) {
		if (index > sheet.getSprites().length - 1) {
			System.err.println("Index out of Bounds in " + this);

			// use a default value
			index = 0;
		}
		sprite = sheet.getSprites()[index];

	}

	/**
	 * check if the animation has played once
	 * 
	 * @return
	 */
	public boolean wasPlayed() {
		return played;
	}

	/**
	 * reset the animation to the start
	 */
	public void reset() {
		played = false;
		frame = 0;
		time = 0;
	}

}
