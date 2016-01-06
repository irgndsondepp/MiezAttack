package com.irgndsondepp.clone.graphics;

import java.util.Random;
import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.entity.Item;
import com.irgndsondepp.clone.entity.MedKit;
import com.irgndsondepp.clone.entity.mob.BurningChaser;
import com.irgndsondepp.clone.entity.mob.Chaser;
import com.irgndsondepp.clone.entity.mob.IntelligentMob;
import com.irgndsondepp.clone.entity.mob.Mob;
import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.level.tile.Tile;

/**
 * the screen class which holds the pixels copied to buffer in the game class. a
 * screen has a width and height, a pixels array (integer), x- and y-offsets (to
 * scroll the content on screen). the class is also set up to be able to create
 * a menu screen with a menu boolean-value.
 * 
 * @author Robert
 * 
 */
public class Screen {

	public int width, height;
	public int[] pixels;

	// never used:
	// public final int MAP_SIZE = 8;
	// public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int xOffset, yOffset;

	// never used:
	// public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	// probability to create a subclass menuScreen
	public boolean menu = false;

	// never used:
	// private Random random = new Random();

	/**
	 * creates a screen with an empty pixels array (width*height)
	 * 
	 * @param width
	 *            is the width
	 * @param height
	 *            is the height
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		// for (int i = 0; i < tiles.length; i++) {
		// tiles[i] = random.nextInt(0xffffff);
		// }
	}

	/**
	 * sets all pixel integer values to 0
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	/**
	 * renders a spritesheet on screen
	 * 
	 * @param xpos
	 *            is the x-position where the sheet is to be drawn
	 * @param ypos
	 *            is the y-position where the sheet is to be drawn
	 * @param sheet
	 *            is the SpriteSheet to be rendered
	 * @param fixed
	 *            if true the SpriteSheet is printed at a fixed position in the
	 *            level, else the SpriteSheet will always be at the same
	 *            position on screen
	 */
	public void renderSpriteSheet(int xpos, int ypos, SpriteSheet sheet,
			boolean fixed) {
		if (fixed) {
			xpos -= xOffset;
			ypos -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + ypos;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xpos;
				// only do the operation if the spritesheet will be visible on
				// screen
				if (xa < -sheet.WIDTH || xa >= width || ya < -sheet.HEIGHT
						|| ya >= height)
					break;
				if (xa >= 0 && ya >= 0) {
					int col = sheet.pixels[x + y * sheet.WIDTH];
					if (col != 0xffff00ff) {
						pixels[xa + ya * width] = col;
					}
				}
			}
		}

	}

	/**
	 * renders a sprite on screen. to do this the renderScaledSprite method is
	 * called with scale set to 1.
	 * 
	 * @param xpos
	 *            is the x-position of the sprite
	 * @param ypos
	 *            is the y-position of the sprite
	 * @param sprite
	 *            the sprite to be rendered
	 * @param fixed
	 *            if true the sprite will stay fixed in the level, otherwise the
	 *            sprite will be at a specific position on screen
	 */
	public void renderSprite(int xpos, int ypos, Sprite sprite, boolean fixed) {
		renderScaledSprite(xpos, ypos, 1, sprite, fixed, true);
	}

	/**
	 * renders an item object on screen. this is a different method to be able
	 * to color different items
	 * 
	 * @param xpos
	 *            is the x position in the level
	 * @param ypos
	 *            is the y position in the level
	 * @param item
	 *            is the item to be drawn
	 * @param fixed
	 *            if true the item is fixed in the level, otherwise it is fixed
	 *            on the screen
	 */
	public void renderItem(int xpos, int ypos, Item item, boolean fixed,
			boolean dynLighting) {
		// can be fixed
		if (fixed) {
			xpos -= xOffset;
			ypos -= yOffset;
		}

		// should be scaleable
		int scale = item.getScale();
		Sprite sprite = item.getSprite();
		for (int y = 0; y < item.getSprite().SIZE; y++) {
			for (int s = 0; s < scale; s++) {
				for (int x = 0; x < item.getSprite().SIZE; x++) {
					for (int t = 0; t < scale; t++) {

						int ya = y * scale + s + ypos;
						int ys = y;
						int xa = x * scale + t + xpos;
						int xs = x;
						if (xa < -scale * sprite.getWidth() || xa >= width
								|| ya < -scale * sprite.getHeight()
								|| ya >= height)
							break;
						if (xa >= 0 && ya >= 0) {

							int col = sprite.pixels[xs + ys * sprite.getWidth()];
							// 0x->Hexzahl ff->alpha ff->R, 00->G, ff->B ==>
							// pink
							if (item instanceof MedKit) {
								if ((((MedKit) item).getHealingValue() >= 50)
										&& (col == 0xffc6c6c6)) {
									col = 0xff1e00ff;
								}
							}
							if (col != 0xffFF00FF) {
								pixels[xa + ya * width] = col;
							}
						}
					}
				}
			}
		}

	}

	/**
	 * uses the renderSprite method with the tiles sprite
	 * 
	 * @param xpos
	 *            is the x position of the tile in the level
	 * @param ypos
	 *            is the y position of the tile in the level
	 * @param tile
	 *            is the tile to be drawn
	 */
	public void renderTile(int xpos, int ypos, Tile tile) {
		renderSprite(xpos, ypos, tile.sprite, true);
	}

	/**
	 * renders a projectile using the renderSprite method
	 * 
	 * @param xpos
	 *            is the x position of the projectile
	 * @param ypos
	 *            is the y position of the projectile
	 * @param p
	 *            is a projectile
	 */
	public void renderProjectile(int xpos, int ypos, Projectile p) {

		renderSprite(xpos, ypos, p.getSprite(), true);
	}

	/**
	 * render scaled mobs. the method will get the scale from the mob and color
	 * different types of enemies.
	 * 
	 * @param mob
	 *            is the enemy / mobile entity to be rendered
	 * @param xpos
	 *            is the x position in the level
	 * @param ypos
	 *            is the y position in the level
	 */
	public void renderMob(Mob mob, int xpos, int ypos) {
		xpos -= xOffset;
		ypos -= yOffset;
		Sprite sprite = mob.getSprite();
		int scale = mob.getScale();

		for (int y = 0; y < sprite.getHeight(); y++) {

			for (int x = 0; x < sprite.getWidth(); x++) {
				for (int s = 0; s < scale; s++) {
					for (int t = 0; t < scale; t++) {

						int ya = y * scale + t + ypos;
						int ys = y;
						int xa = x * scale + s + xpos;
						int xs = x;
						// only do stuff if the object is visible on screen
						if (xa < -scale * sprite.getWidth() || xa >= width
								|| ya < -scale * sprite.getHeight()
								|| ya >= height)
							break;
						// only draw valid values
						if (xa >= 0 && ya >= 0) {

							int col = sprite.pixels[xs + ys * sprite.getWidth()];
							// 0x->Hexzahl ff->alpha ff->R, 00->G, ff->B ==>
							// pink
							if (col != 0xffFF00FF) {
								if (((mob instanceof Chaser)
										&& col <= 0xffffffff && col >= 0xffdadada)) {
									col = 0xffdc0000;
								}
								if (mob instanceof BurningChaser
										&& col <= 0xffffffff
										&& col >= 0xffdadada
										&& !(col == 0xffff0000)
										&& !(col == 0xffff5e00)
										&& !(col == 0xffffd200)) {
									col = 0xffdc0000;
								}
								if ((mob instanceof IntelligentMob
										&& col <= 0xffffffff && col >= 0xffdadada)) {
									col = 0xff298e37;
								}

								pixels[xa + ya * width] = col;

							}
						}
					}
				}
			}
		}

	}

	/**
	 * set the offset of the top left screen corner to the position in the
	 * level.
	 * 
	 * @param xOffset
	 *            offset in x direction
	 * @param yOffset
	 *            offset in y direction
	 */
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * renders a scaled Sprite.
	 * 
	 * @param xpos
	 *            is the x position of the sprite
	 * @param ypos
	 *            is the y position of the sprite
	 * @param scale
	 *            is the scale of the sprite
	 * @param sprite
	 *            is the sprite to be drawn
	 */
	public void renderScaledSprite(int xpos, int ypos, int scale,
			Sprite sprite, boolean fixed, boolean dynLighting) {
		if (fixed) {
			xpos -= xOffset;
			ypos -= yOffset;
		}

		for (int y = 0; y < sprite.getHeight(); y++) {
			for (int s = 0; s < scale; s++) {
				for (int x = 0; x < sprite.getWidth(); x++) {

					for (int t = 0; t < scale; t++) {

						int ya = y * scale + s + ypos;
						int ys = y;
						int xa = x * scale + t + xpos;
						int xs = x;
						if (xa < -scale * sprite.getWidth() || xa >= width
								|| ya < -scale * sprite.getHeight()
								|| ya >= height)
							break;
						if (xa >= 0 && ya >= 0) {

							int col = sprite.pixels[xs + ys * sprite.getWidth()];
							// 0x->Hexzahl ff->alpha ff->R, 00->G, ff->B ==>
							// pink
							if (col != 0xffFF00FF) {
								pixels[xa + ya * width] = col;

							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method will create a glowing ball of light
	 * 
	 * @param col
	 * @return
	 */
	public void AddDynamicLighting(int xa, int ya, int radius, int intensity) {
		if (Game.dynamicLighting < 1)
			return;
		xa -= this.xOffset;
		ya -= this.yOffset;

		for (int x = xa - radius; x < xa + radius; x++) {
			for (int y = ya - radius; y < ya + radius; y++) {
				if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
					continue;
				}

				Random random = new Random();
				double factor = Math.pow(xa - x, 2) + Math.pow(ya - y, 2);
				if (factor > Math
						.pow((radius - (radius / 3) + random
								.nextInt(radius / 3)), 2)) {
					continue;
				}
				factor = Math.sqrt(factor);
				pixels[x + y * width] = darkenColorAndGlow(
						pixels[x + y * width], factor, intensity);
				/*
				 * String hexValues = Integer.toHexString(pixels[x + y *
				 * width]);
				 * 
				 * if (hexValues.length() < 8) continue; int col = 0; for (int i
				 * = 0; i < 4; i++) { if (i == 0) { col += 0xff000000; continue;
				 * } String parseValue = hexValues.substring(i * 2, i * 2 + 2);
				 * int n = (int) Long.parseLong(parseValue, 16); n += intensity;
				 * if (n > 255) { n = 255; }
				 * 
				 * if (i != 1) { n -= (int) factor; } else { n -= (int) 0.2 *
				 * factor; } if (n < 0) { n = 0; }
				 * 
				 * col += (int) (n * Math.pow(16, (3 - i) * 2)); // val[i] = n +
				 * temp;// + (int) 0.2 * intensity - temp;
				 * 
				 * } pixels[x + y * width] = col;
				 */
			}
		}

	}

	/**
	 * This method will darken the color so a radius of light around the player
	 * is achieved.
	 */
	public void lightRadius() {
		int radius = 70;
		if (Game.dynamicLighting < 2)
			return;
		Random random = new Random();
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				double factor = Math.pow(this.width / 2 - x, 2)
						+ Math.pow(this.height / 2 - y, 2);
				if (factor < Math.pow(radius + random.nextInt(5), 2)) {
					continue;
				}
				factor = Math.sqrt(factor);
				pixels[x + y * this.width] = darkenColor(pixels[x + y
						* this.width], factor);
			}
		}
	}

	private int darkenColor(int col, double factor) {
		String hexValues = Integer.toHexString(col);
		String darkerCol = "ff";
		if (hexValues.length() < 8)
			return col;
		for (int i = 1; i < 4; i++) {
			String parseValue = hexValues.substring(i * 2, i * 2 + 2);
			int n = (int) Long.parseLong(parseValue, 16);
			int temp = (int) (0.5 * factor);
			n = n + 10 - temp;
			if (n < 0) {
				n = 0;
			} else if (n > 255) {
				n = 255;
			}
			String hexString = Integer.toHexString(n);
			if (hexString.length() < 2) {
				darkerCol += "0";
			}
			darkerCol += Integer.toHexString(n);
		}
		return (int) Long.parseLong(darkerCol, 16);
	}

	private int darkenColorAndGlow(int col, double factor, int intensity) {		
		String hexValues = Integer.toHexString(col);
		String darkerCol = "ff";
		if (hexValues.length() < 8)
			return col;
		for (int i = 1; i < 4; i++) {
			String parseValue = hexValues.substring(i * 2, i * 2 + 2);
			int n = (int) Long.parseLong(parseValue, 16);
			int temp = (int) (0.5 * factor);
			n += intensity;
			if (n < 0) {
				n = 0;
			} else if (n > 255) {
				n = 255;
			}
			String hexString = Integer.toHexString(n);
			if (hexString.length() < 2) {
				darkerCol += "0";
			}
			darkerCol += Integer.toHexString(n);
		}
		return (int) Long.parseLong(darkerCol, 16);
	}

	/**
	 * create and render a health bar on screen. the percentage filled with red
	 * is calculated by the two input values.
	 * 
	 * @param hitpoints
	 *            is the actual hitpoints of a boss
	 * @param maxHitpoints
	 *            is the maximum hitpoints of a boss
	 */
	public void renderHealthBar(double hitpoints, double maxHitpoints) {
		// create a new pixel array
		int size = width / 2;
		int hSize = height / 20;
		int[] healthBar = new int[size * hSize];

		// how much of it should be filled with red?
		int percentage = (int) ((hitpoints / maxHitpoints) * size);
		for (int y = 0; y < hSize; y++) {
			for (int x = 0; x < size; x++) {
				if (y == 0 || x == 0 || (x % (size / 20)) == 0) {
					healthBar[x + y * size] = 0xff000000;
				} else if (x < percentage) {
					healthBar[x + y * size] = 0xffff0000;
				} else {
					healthBar[x + y * size] = -1;
				}
			}
		}

		// fixed position on screen
		int ypos = hSize;
		int xpos = size / 2;

		for (int y = 0; y < hSize; y++) {
			int ya = y + ypos;
			int ys = y;
			for (int x = 0; x < size; x++) {
				int xa = x + xpos;
				int xs = x;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa >= 0 && ya >= 0) {

					int col = healthBar[xs + ys * size];
					// 0x->Hexzahl ff->alpha ff->R, 00->G, ff->B ==> pink
					if (col != -1) {
						pixels[xa + ya * width] = col;

					}
				}
			}
		}

	}

	/**
	 * fades the screen to black, starting from the edges and going to the
	 * middle. a factor is calculated by taking an updated time value and a
	 * maximum value to only fill part of the screen with black. speed is used
	 * to quicken or shorten the animation. if time is longer than the maximum
	 * time the whole screen is filled with black.
	 * 
	 * @param time
	 *            is used to determine the step in the animation
	 * @param timeMax
	 *            is the maximum time the animation should run
	 * @param speed
	 *            is the speed with which the animation is drawn
	 */
	public void fadeToBlack(int time, int timeMax, int speed) {
		double t = (double) time * speed;
		double tm = (double) timeMax;
		double factor = 0;
		if (t <= tm) {
			factor = t / tm;
		} else {
			factor = 1.f;
		}
		int w = (int) (((double) width) * factor / 2);
		int h = (int) (((double) height) * factor / 2);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((x <= w) || (x >= width - w) || (y <= h)
						|| (y >= height - h)) {
					pixels[x + y * width] = 0xff000000;

				}
			}
		}

	}
}
